package top.zopx.goku.framework.tools.util.reflection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * 动态扫描指定包下类
 *
 * @author Mr.Xie
 * @date 2021/2/8
 */
@SuppressWarnings("all")
public enum PackageUtil {

    /**
     * 单例实例
     */
    INSTANCE;

    PackageUtil() {
    }

    /**
     * 获取指定包下的所有的文件
     * 利用队列实现遍历
     *
     * @param packageName 包名
     * @param superClass  父类
     * @param isRecursion 是否迭代
     * @return List<Class < ?>>
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public List<Class<?>> getFileList(String packageName, Class<?> superClass, boolean isRecursion) throws IOException, ClassNotFoundException {
        if (null == superClass || StringUtils.isBlank(packageName)) {
            return Collections.emptyList();
        } else {
            return getFileListBySuperClass(packageName, isRecursion, superClass::isAssignableFrom);
        }

    }

    private static final String separator() {
        return !System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux") ? "/" : File.separator;
    }

    public List<Class<?>> getFileListBySuperClass(
            String packageName,
            boolean isRecursion,
            Function<Class<?>, Boolean> func
    ) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = Collections.emptyList();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final String packagePath = packageName.replace(".", separator());

        Enumeration<URL> enumeration = loader.getResources(packagePath);

        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();

            String protocol = url.getProtocol();

            if ("file".equalsIgnoreCase(protocol)) {
                classList = list2Dir(new File(url.getFile()), packageName, isRecursion, func);
            } else if ("jar".equalsIgnoreCase(protocol)) {
                // 获取文件字符串
                String fileStr = url.getFile();

                if (fileStr.startsWith("file:")) {
                    // 如果是以 "file:" 开头的,
                    // 则去除这个开头
                    fileStr = fileStr.substring(5);
                }

                if (fileStr.lastIndexOf('!') > 0) {
                    // 如果有 '!' 字符,
                    // 则截断 '!' 字符之后的所有字符
                    fileStr = fileStr.substring(0, fileStr.lastIndexOf('!'));
                }

                // 从 JAR 文件中加载类
                classList = listClazzFromJar(
                        new File(fileStr), packageName, isRecursion, func
                );
            }
        }

        return classList;
    }

    private List<Class<?>> listClazzFromJar(File file, String packageName, boolean isRecursion, Function<Class<?>, Boolean> filter) throws IOException, ClassNotFoundException {
        if (file == null ||
                file.isDirectory()) {
            // 如果参数对象为空,
            // 则直接退出!
            return Collections.emptyList();
        }

        List<Class<?>> resultList = new ArrayList<>();

        try (JarInputStream jarIn = new JarInputStream(new FileInputStream(file))) {
            // 进入点
            JarEntry entry;

            while ((entry = jarIn.getNextJarEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                // 获取进入点名称
                String entryName = entry.getName();

                if (!entryName.endsWith(".class")) {
                    // 如果不是以 .class 结尾,
                    // 则说明不是 JAVA 类文件, 直接跳过!
                    continue;
                }

                if (!isRecursion) {
                    //
                    // 如果没有开启递归模式,
                    // 那么就需要判断当前 .class 文件是否在指定目录下?
                    //
                    // 获取目录名称
                    String tmpStr = entryName.substring(0, entryName.lastIndexOf(File.separator));
                    // 将目录中的 "/" 全部替换成 "."
                    tmpStr = join(tmpStr.split(File.separator), ".");

                    if (!packageName.equals(tmpStr)) {
                        // 如果包名和目录名不相等,
                        // 则直接跳过!
                        continue;
                    }
                }

                String clazzName;

                // 清除最后的 .class 结尾
                clazzName = entryName.substring(0, entryName.lastIndexOf('.'));
                // 将所有的 / 修改为 .
                clazzName = join(clazzName.split(File.separator), ".");

                // 加载类定义
                Class<?> clazzObj = Class.forName(clazzName);

                if (null != filter &&
                        !filter.apply(clazzObj)) {
                    // 如果过滤器不为空,
                    // 且过滤器不接受当前类,
                    // 则直接跳过!
                    continue;
                }

                // 添加类定义到集合
                resultList.add(clazzObj);
            }
        }
        return resultList;
    }

    /**
     * 使用连接符连接字符串数组
     *
     * @param strArr 字符串数组
     * @param conn   连接符
     * @return 连接后的字符串
     */
    static private String join(String[] strArr, String conn) {
        if (null == strArr ||
                strArr.length <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                // 添加连接符
                sb.append(conn);
            }

            // 添加字符串
            sb.append(strArr[i]);
        }

        return sb.toString();
    }

    private List<Class<?>> list2Dir(File dirFile, String packageName, boolean isRecursion, Function<Class<?>, Boolean> filter) throws ClassNotFoundException {
        List<Class<?>> resultList = Collections.emptyList();

        // 判断文件是否存在
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return resultList;
        }

        File[] subFiles = dirFile.listFiles();
        if (null == subFiles || ArrayUtils.isEmpty(subFiles)) {
            return resultList;
        }

        // 将子文件列表添加到队列中
        LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>(Arrays.asList(subFiles));
        resultList = new ArrayList<>(queue.size());

        while (!queue.isEmpty()) {
            // 从头部取出一个元素
            File poll = queue.poll();
            if (poll.isDirectory() && isRecursion) {
                // 子元素添加到队列中
                subFiles = poll.listFiles();
                if (null == subFiles || ArrayUtils.isEmpty(subFiles)) {
                    continue;
                }
                queue.addAll(Arrays.asList(subFiles));
            }

            if (!poll.isFile() || !poll.getName().endsWith(".class")) {
                continue;
            }

            String clazzName = poll.getAbsolutePath();

            clazzName = clazzName.substring(dirFile.getAbsolutePath().length(), clazzName.lastIndexOf("."));
            clazzName = clazzName.replace(File.separator, ".");
            clazzName = packageName + clazzName;

            Class<?> returnClass = Class.forName(clazzName);

            if (null != filter && !filter.apply(returnClass)) {
                continue;
            }

            resultList.add(returnClass);

        }
        return resultList;
    }

    /**
     * 检测当前类的修饰符
     * if(isModifier(T.class, Modifier.ABSTRACT)) {
     * // 是ABSTRACT修饰的
     * }
     *
     * @param clazz     类
     * @param modifiers 修饰符
     * @return false 不是modifiers修饰
     * true  是modifiers修饰的
     */
    public boolean isModifier(Class<?> clazz, int modifiers) {

        if (null == clazz) {
            return false;
        }

        return (clazz.getModifiers() & modifiers) != 0;
    }

    /**
     * 缓存
     */
    private final Map<String, List<Class<?>>> TYPE_MAP = new WeakHashMap<>(8);

    /**
     * 得到指定类中指定方法的参数
     *
     * @param superClass 指定类
     * @param methodName 方法名称
     * @param filter     过滤器
     * @return List<Class < ?>>
     */
    public List<Class<?>> getType(Class<?> superClass, String methodName, Function<Class<?>, Boolean> filter) {

        if (null == superClass || StringUtils.isBlank(methodName)) {
            return Collections.emptyList();
        }

        List<Class<?>> cacheList = TYPE_MAP.get(methodName);

        if (CollectionUtils.isNotEmpty(cacheList)) {
            return cacheList;
        }

        Method[] methods = superClass.getDeclaredMethods();

        if (ArrayUtils.isEmpty(methods)) {
            return Collections.emptyList();
        }

        List<Class<?>> returnList = new ArrayList<>();
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            Class<?>[] types = method.getParameterTypes();
            for (Class<?> type : types) {
                if (null != filter && !filter.apply(type)) {
                    continue;
                }

                returnList.add(type);
            }
        }
        if (CollectionUtils.isNotEmpty(returnList)) {
            TYPE_MAP.put(methodName, returnList);
        }

        return returnList;
    }
}
