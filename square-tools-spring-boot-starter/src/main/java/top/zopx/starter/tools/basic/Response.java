package top.zopx.starter.tools.basic;

import top.zopx.starter.tools.constants.BusCode;

import java.io.Serializable;

/**
 *  统一json返回格式
 *      推荐使用 R<T>
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Deprecated
public class Response implements Serializable {

    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    /**
     * 提示信息
     */
    private Meta meta;
    /**
     * 返回数据
     */
    private Object data;

    public Response() {
    }

    public Response(Meta meta, Object data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Response success() {
        return success(true);
    }

    public Response success(Object data) {
        this.meta = new Meta(true, OK, BusCode.RESULT_OK);
        this.data = data;
        return this;
    }

    public Response failure() {
        return failure(ERROR);
    }

    public Response failure(String message) {
        return failure(message, BusCode.RESULT_ERROR);
    }

    public Response failure(String message, Integer code) {
        this.meta = new Meta(false, message, code);
        return this;
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
