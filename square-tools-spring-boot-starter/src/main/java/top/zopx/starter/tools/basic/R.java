package top.zopx.starter.tools.basic;

import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.io.Serializable;

/**
 * 封装返回数据
 * @author sanq.Yan
 * @date 2021/5/3
 */
public final class R<T> implements Serializable {

    private Response.Meta meta;

    private T data;

    public R() {
    }

    public R(Response.Meta meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    public Response.Meta getMeta() {
        return meta;
    }

    public void setMeta(Response.Meta meta) {
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
     * @param data true || false
     * @return R<Boolean>
     */
    public static R<Boolean> status(boolean data) {
        return status(data, "");
    }

    /**
     * 返回状态
     * @param data true || false
     * @param msg  返回消息
     * @return R<Boolean>
     */
    public static R<Boolean> status(boolean data, String msg) {
        return new R<>(
                new Response.Meta(
                        data,
                        data ? Response.OK : StringUtil.isNotBlank(msg) ? msg : Response.ERROR,
                        data ? BusCode.RESULT_OK : BusCode.RESULT_ERROR
                ),
                data
        );
    }

    /**
     * 成功返回
     * @param data 数据
     * @param <T> 泛型类型
     * @return R<T>
     */
    public static <T> R<T> success(T data) {
        return new R<>(
                new Response.Meta(
                        true,
                        Response.OK,
                        BusCode.RESULT_OK
                ),
                data
        );
    }
}
