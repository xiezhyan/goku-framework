package top.zopx.goku.framework.tools.entity.wrapper;

import top.zopx.goku.framework.tools.exceptions.IBus;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.io.Serializable;

/**
 * 封装返回数据
 *
 * @author 俗世游子
 * @date 2021/5/3
 */
public final class R<T> implements Serializable {

    public static final String OK = "OK";

    public static final String ERROR = "ERROR";

    private Meta meta;

    private T data;

    public R() {
    }

    public R(Meta meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 返回状态
     *
     * @param data true || false
     * @return R<Boolean>
     */
    public static R<Boolean> status(boolean data) {
        return status(data, "");
    }

    /**
     * 返回状态
     *
     * @param data true || false
     * @param msg  返回消息
     * @return R<Boolean>
     */
    public static R<Boolean> status(boolean data, String msg) {
        return result(
                data,
                new Meta(true, StringUtil.isNotBlank(msg) ? msg : ERROR, 200)

        );
    }

    /**
     * 返回对象
     *
     * @param data 数据
     * @param <T>  泛型类型
     * @return R<T>
     */
    public static <T> R<T> result(T data) {
        return result(
                data,
                data == null ?
                        new Meta(
                                false,
                                ERROR,
                                400
                        ) :
                        new Meta(
                                true,
                                OK,
                                200
                        )
        );
    }

    public static <T> R<T> result(T data, Meta meta) {
        return new R<>(meta, data);
    }

    /**
     * 失败
     *
     * @param msg  失败说明
     * @param code 失败码
     * @return R<T>
     */
    public static <T> R<T> failure(String msg, Integer code) {
        return result(
                null,
                new Meta(false, msg, code)
        );
    }

    /**
     * 失败
     *
     * @param bus 消息编码
     * @return R<T>
     */
    public static <T> R<T> failure(IBus bus) {
        return failure(bus.getMsg(), bus.getCode());
    }

    public static class Meta implements Serializable {
        /**
         * 操作是否成功
         */
        private Boolean success;
        /**
         * 返回消息
         */
        private String message;
        /**
         * 返回编码
         */
        private Integer code;

        public Meta() {
        }

        public Meta(Boolean success, String message, Integer code) {
            this.success = success;
            this.message = message;
            this.code = code;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }
}
