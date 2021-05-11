package top.zopx.starter.tools.basic;

import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.io.Serializable;

/**
 * 封装返回数据
 *
 * @author sanq.Yan
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
        return new R<>(
                new Meta(
                        data,
                        data ? OK : StringUtil.isNotBlank(msg) ? msg : ERROR,
                        data ? BusCode.RESULT_OK : BusCode.RESULT_ERROR
                ),
                data
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
                                BusCode.RESULT_ERROR
                        ) :
                        new Meta(
                                true,
                                OK,
                                BusCode.RESULT_OK
                        )
        );
    }

    public static <T> R<T> result(T data, Meta meta) {
        return new R<>(
                meta,
                data
        );
    }

    /**
     * 失败
     *
     * @param msg  失败说明
     * @param code 失败码
     * @return R<T>
     */
    public static <T> R<T> failure(String msg, Integer code) {
        return new R<>(
                new Meta(
                        false,
                        msg,
                        code),
                null
        );
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
    }
}
