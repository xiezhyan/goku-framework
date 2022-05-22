package top.zopx.goku.framework.web.util;

import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.constant.UserBusCons;
import top.zopx.goku.framework.web.entity.UserLoginVO;

import java.util.Optional;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:09
 */
public class UserLoginHelper {

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
    public static boolean isLogin() {
        try {
            return Optional.of(THREAD_LOCAL.get()).isPresent();
        } catch (Exception e) {
            throw new BusException(UserBusCons.NOT_LOGIN);
        }
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
    public static Long getUserIdOrNull() {
        final Optional<UserLoginVO> optional = getUserOrNull();
        return optional.map(UserLoginVO::getUserId).orElse(null);
    }
}
