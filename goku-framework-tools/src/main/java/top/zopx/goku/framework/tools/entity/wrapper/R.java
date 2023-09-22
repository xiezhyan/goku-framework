package top.zopx.goku.framework.tools.entity.wrapper;

import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.tools.exception.IBus;

import java.io.Serializable;

/**
 * 封装返回数据
 *
 * @author Mr.Xie
 * @date 2021/5/3
 */
@SuppressWarnings("all")
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
    @SuppressWarnings("all")
    public static R<Boolean> status(boolean data, String msg) {
        return result(
                data,
                new Meta(true, data ? OK : StringUtils.isNotBlank(msg) ? msg : ERROR, data ? 200 : 400)
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

    /**
     * Builder构建
     *
     * @param <T> 类型
     * @return Builder
     */
    public static <T> Builder<T> create() {
        return new Builder<>();
    }

    public static class Meta implements Serializable, IBus {
        /**
         * 操作是否成功
         */
        private Boolean success;
        /**
         * 返回消息
         */
        private String msg;
        /**
         * 返回编码
         */
        private int code;

        public Meta() {
        }

        public Meta(Boolean success, String msg, int code) {
            this.success = success;
            this.msg = msg;
            this.code = code;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public static class Builder<T> {

        private Meta meta = new Meta(true, "OK", 200);
        private T data;

        private Builder() {
        }

        public Builder<T> setMeta(IBus cons) {
           return setMeta(new Meta(false, cons.getMsg(), cons.getCode()));
        }

        public Builder<T> setMeta(Boolean success, String message, Integer code) {
            return setMeta(new Meta(success, message, code));
        }

        public Builder<T> setMeta(Meta meta) {
            this.meta = meta;
            return this;
        }

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public R<T> build() {
            return R.result(data, meta);
        }
    }
}
