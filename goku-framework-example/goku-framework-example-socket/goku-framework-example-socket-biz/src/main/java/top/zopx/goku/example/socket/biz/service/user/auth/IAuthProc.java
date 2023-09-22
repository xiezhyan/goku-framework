package top.zopx.goku.example.socket.biz.service.user.auth;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public interface IAuthProc {

    /**
     * 异步操作 Id, 主要用于分派登录线程
     *
     * @return 临时 Id
     */
    int getAsyncOpId();

    /**
     * 获取临时 Id
     *
     * @return 临时 Id
     */
    String getTempId();

    /**
     * 执行授权
     *
     * @return 用户 Id
     */
    long doAuth();

}
