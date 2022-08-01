package top.zopx.goku.framework.cluster.entity;

import top.zopx.goku.framework.cluster.constant.IErrorBus;

import java.io.Serializable;

/**
 * 封装返回数据
 *
 * @author sanq.Yan
 * @date 2021/5/3
 */
public final class R<T> implements Serializable {

    private final Meta meta;

    private final T data;

    private R(Meta meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
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

    @Override
    public String toString() {
        return "R{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }

    public static <T> R<T> result(T data, Meta meta) {
        return new R<>(
                meta,
                data
        );
    }

    public static class Builder<T> {
        private Meta meta;
        private T data;

        private Builder() {
        }

        public Builder<T> setMeta(IErrorBus cons) {
            this.meta = new Meta(cons.getSuccess(), cons.getMessage(), cons.getCode());
            return this;
        }

        public Builder<T> setMeta(Boolean success, String message, Integer code) {
            this.meta = new Meta(success, message, code);
            return this;
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


    public static class Meta implements Serializable {
        private Boolean success;
        private String message;
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

        @Override
        public String toString() {
            return "Meta{" +
                    "success=" + success +
                    ", message='" + message + '\'' +
                    ", code=" + code +
                    '}';
        }
    }
}
