package top.zopx.starter.tools.tools.reflection;

import org.apache.commons.lang3.ArrayUtils;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

/**
 * 动态扫描指定包下类
 *
 * @author sanq.Yan
 * @date 2021/2/8
 */
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

        if (null == superClass || StringUtil.isBlank(packageName)) {
            return Collections.emptyList();
        } else {
            return getFileListBySuperClass(packageName, isRecursion, superClass::isAssignableFrom);
        }

    }

    private List<Class<?>> getFileListBySuperClass(
            String packageName,
            boolean isRecursion,
            Function<Class<?>, Boolean> filter
    ) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = Collections.emptyList();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final String packagePath = packageName.replace(".", "/");

        Enumeration<URL> enumeration = loader.getResources(packagePath);

        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();

            String protocol = url.getProtocol();

            if ("file".equalsIgnoreCase(protocol)) {
                classList = list2Dir(new File(url.getFile()), packageName, isRecursion, filter);
            }
        }

        return classList;
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
            clazzName = clazzName.replace("\\", ".");
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
     * 得到指定类中指定方法的参数
     *
     * @param superClass 指定类
     * @param methodName 方法名称
     * @param filter     过滤器
     * @return List<Class < ?>>
     */
    public List<Class<?>> getType(Class<?> superClass, String methodName, Function<Class<?>, Boolean> filter) {
        List<Class<?>> returnList = Collections.emptyList();

        if (null == superClass || StringUtil.isBlank(methodName)) {
            return returnList;
        }

        Method[] methods = superClass.getDeclaredMethods();

        if (ArrayUtils.isEmpty(methods)) {
            return returnList;
        }

        returnList = new ArrayList<>();
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

        return returnList;
    }
}
