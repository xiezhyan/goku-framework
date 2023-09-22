package top.zopx.goku.example.socket.biz.service.user;


import top.zopx.goku.example.socket.biz.service.user.mod.UserService$Login;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public final class UserService implements UserService$Login {

    private UserService() {}

    private static class Holder {
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }
}
