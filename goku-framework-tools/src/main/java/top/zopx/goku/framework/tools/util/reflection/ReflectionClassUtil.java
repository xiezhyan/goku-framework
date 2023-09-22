package top.zopx.goku.framework.tools.util.reflection;

import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 反射操作
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/5/25
 */
public final class ReflectionClassUtil {

    private ReflectionClassUtil() {
    }

    private static final String GET = "get";
    private static final String SET = "set";

    /**
     * 通过Field执行get方法
     *
     * @param value 类
     * @param field 字段属性
     * @return 返回数据
     */
    public static Object invokeGetMethod(Object value, Field field) {
        return invokeGetMethod(value, field.getName());
    }

    /**
     * 通过字段名称执行get方法
     *
     * @param value     类
     * @param fieldName 字段名称
     * @return 返回数据
     */
    public static Object invokeGetMethod(Object value, String fieldName) {
        String getMethodName = GET.concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1));
        try {
            return invokeGetMethod(value, getMethodName, value.getClass());
        } catch (Exception e) {
            try {
                return invokeGetMethod(value, getMethodName, value.getClass().getSuperclass());
            } catch (Exception ex) {
                throw new BusException(ex.getMessage(), IBus.ERROR_CODE);
            }
        }
    }

    /**
     * 指定set方法
     *
     * @param value 类
     * @param field 字段
     * @param param 参数
     */
    public static void invokeSetMethod(Object value, Field field, Object param) {
        invokeSetMethod(value, field.getName(), param);
    }

    /**
     * 指定set方法
     *
     * @param value     类
     * @param fieldName 字段
     * @param param     参数
     */
    public static void invokeSetMethod(Object value, String fieldName, Object param) {
        String setMethodName = SET.concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1));
        try {
            invokeSetMethod(value, setMethodName, param, value.getClass());
        } catch (Exception e) {
            try {
                invokeSetMethod(value, setMethodName, param, value.getClass().getSuperclass());
            } catch (Exception ex) {
                throw new BusException(ex.getMessage(), IBus.ERROR_CODE);
            }
        }
    }

    /**
     * 获取指定方法
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param clazzs     参数类型
     * @return Method
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... clazzs) {
        try {
            return clazz.getDeclaredMethod(methodName, clazzs);
        } catch (NoSuchMethodException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (Objects.nonNull(superclass)) {
                return getMethod(superclass, methodName, clazzs);
            }
        }
        throw new BusException("reflection.declared.error", IBus.ERROR_CODE);
    }

    /**
     * 获取到指定类的字段信息， 包含父类
     *
     * @param value 类
     * @return List<Field>
     */
    public static List<Field> getFieldList(Object value) {
        if (Objects.isNull(value)) {
            return new ArrayList<>(0);
        }
        List<Field> fields = Arrays.stream(value.getClass().getDeclaredFields()).collect(Collectors.toList());

        Class<?> superclass = value.getClass().getSuperclass();
        if (Objects.nonNull(superclass) && !Objects.equals(superclass, Object.class)) {
            fields.addAll(
                    Stream.of(superclass.getDeclaredFields()).toList()
            );
        }
        return fields.stream().filter(field -> !Modifier.isStatic(field.getModifiers())).toList();
    }

    /**
     * 获取指定类型的泛型类型
     *
     * @param clazz 类实现接口的泛型
     * @return List<Class < ?>>
     */
    @SuppressWarnings("all")
    public static List<Class<?>> getGeneric(Class<?> clazz) {
        return Arrays.stream(clazz.getGenericInterfaces()).map(type -> {
            if (type instanceof ParameterizedType parameterizedType) {
                return Arrays.stream(parameterizedType.getActualTypeArguments()).map(actualTypeArgument -> (Class<?>) actualTypeArgument).toList();
            }
            return new ArrayList<Class<?>>(0);
        }).flatMap(List::stream).collect(Collectors.toList());
    }

    private static void invokeSetMethod(Object value, String methodName, Object param, Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Class<?> paramClazz = param.getClass();
        if (param instanceof List) {
            paramClazz = List.class;
        }
        if (param instanceof Set) {
            paramClazz = Set.class;
        }
        if (param instanceof Map) {
            paramClazz = Map.class;
        }
        Method method = getMethod(clazz, methodName, paramClazz);
        method.invoke(value, param);
    }

    private static Object invokeGetMethod(Object value, String methodName, Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Method method = getMethod(clazz, methodName);
        return method.invoke(value);
    }
}