package top.zopx.goku.framework.http.util.login;

import top.zopx.goku.framework.http.constant.ErrorEnum;
import top.zopx.goku.framework.http.entity.vo.UserLoginVO;
import top.zopx.goku.framework.tools.exception.BusException;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:09
 */
public class UserLoginHelper {

    protected UserLoginHelper() {}

    private static final ThreadLocal<UserLoginVO> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserLoginVO userLoginVO) {
        THREAD_LOCAL.set(userLoginVO);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 用户是否已经登录
     * 如果没有用户信息，说明未登录，抛出异常
     *
     * @return
     */
    @SuppressWarnings("all")
    private static boolean isLogin() {
        try {
            return Objects.nonNull(THREAD_LOCAL.get());
        } catch (Exception e) {
            throw new BusException(ErrorEnum.NOT_LOGIN);
        }
    }

    /**
     * 返回登录用户
     *
     * @return UserLoginVO
     */
    public static Optional<UserLoginVO> getUserOrNull() {
        return Optional.ofNullable(THREAD_LOCAL.get());
    }

    /**
     * 获取登录用户ID
     *
     * @return 登录用户ID
     */
    public static Long getUserId() {
        if (isLogin()) {
            return THREAD_LOCAL.get().getUserId();
        }
        return null;
    }

    /**
     * 获取登录用户租户信息
     *
     * @return 租户ID
     */
    public static Long getTenantId() {
        if (isLogin()) {
            return THREAD_LOCAL.get().getTenantId();
        }
        return null;
    }
}
