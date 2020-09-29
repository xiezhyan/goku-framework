package top.zopx.starter.tools.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zopx.starter.tools.constants.BusCode;

import java.io.Serializable;

/**
 * version: 统一json返回格式
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response implements Serializable {

    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    private Meta meta;
    private Object data;

    public Response success() {
        this.meta = new Meta(true, OK, BusCode.RESULT_OK);
        this.data = true;
        return this;
    }

    public Response success(Object data) {
        this.meta = new Meta(true, OK, BusCode.RESULT_OK);
        this.data = data;
        return this;
    }

    public Response failure() {
        this.meta = new Meta(false, ERROR, BusCode.RESULT_ERROR);
        return this;
    }

    public Response failure(String message) {
        this.meta = new Meta(false, message, BusCode.RESULT_ERROR);
        return this;
    }

    public Response failure(String message, Integer code) {
        this.meta = new Meta(false, message, code);
        return this;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Meta implements Serializable {
        private Boolean success;
        private String message;
        private Integer code;
    }
}
