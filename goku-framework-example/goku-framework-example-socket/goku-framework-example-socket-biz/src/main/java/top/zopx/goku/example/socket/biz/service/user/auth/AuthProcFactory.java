package top.zopx.goku.example.socket.biz.service.user.auth;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public final class AuthProcFactory {

    public static IAuthProc create(int loginType, String login) {
        switch (loginType) {
            case 0:
                return new AccountSecretAuthProc(login);
            case 1:
                return new AccountSecretAuthProc(login);
            case 2:
                return new AccountSecretAuthProc(login);
            case 3:
                return new AccountSecretAuthProc(login);
            case 4:
                return new AccountSecretAuthProc(login);
            case 5:
                return new AccountSecretAuthProc(login);
            case 6:
                return new AccountSecretAuthProc(login);
            default:
                return null;
        }
    }

}
