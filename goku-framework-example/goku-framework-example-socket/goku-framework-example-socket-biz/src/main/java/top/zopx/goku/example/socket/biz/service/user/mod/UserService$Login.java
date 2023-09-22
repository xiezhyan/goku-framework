package top.zopx.goku.example.socket.biz.service.user.mod;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.biz.entity.bo.LoginResult;
import top.zopx.goku.example.socket.biz.service.user.auth.AuthProcFactory;
import top.zopx.goku.example.socket.biz.service.user.auth.IAuthProc;
import top.zopx.goku.example.socket.common.async.AsyncOperationProcessorSingleton;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.common.constant.ErrorBusEnum;
import top.zopx.goku.framework.socket.core.ukey.UKey;
import top.zopx.goku.framework.socket.core.util.Out;
import top.zopx.goku.framework.socket.netty.entity.IResultCallback;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.lock.DLock;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.socket.redis.util.Ticket;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public interface UserService$Login {

    Logger LOGGER = LoggerFactory.getLogger(UserService$Login.class);

    default void doLoginAsync(int loginType, String login, IResultCallback<LoginResult> resultX) {
        // 确保不为空
        IResultCallback<LoginResult> callBack = resultX == null ? result -> {
        } : resultX;

        if (StringUtils.isBlank(login)) {
            LOGGER.error(ErrorBusEnum.LOGIN_EMPTY.getMsg());
            callBack.apply(R.failure(ErrorBusEnum.LOGIN_EMPTY));
            return;
        }

        IAuthProc authProc = AuthProcFactory.create(loginType, login);

        if (null == authProc) {
            LOGGER.error(ErrorBusEnum.LOGIN_TYPE_NOT_FIND.getMsg());
            callBack.apply(R.failure(ErrorBusEnum.LOGIN_TYPE_NOT_FIND));
            return;
        }

        R.Builder<LoginResult> builder = R.create();

        AsyncOperationProcessorSingleton.getInstance().process(
                authProc.getAsyncOpId(),
                () -> doLogin(authProc, builder),
                () -> callBack.apply(builder.build())
        );
    }

    default void doLogin(IAuthProc authProc, R.Builder<LoginResult> builder) {

        try (DLock locker = DLock.newLock("do_user_login?temp_id=" + authProc.getTempId())) {
            if (null == locker ||
                    !locker.tryLock(5000)) {
                // 增加一个分布式锁,
                // 5 秒种内不能执行相同操作,
                // 避免重复创建账号...
                builder.setMeta(ErrorBusEnum.ERROR);
                return;
            }

            // 执行授权并返回用户 Id
            final long userId = authProc.doAuth();

            if (userId <= 0) {
                LOGGER.error(ErrorBusEnum.LOGIN_ERROR.getMsg());
                builder.setMeta(ErrorBusEnum.LOGIN_ERROR);
                return;
            }

            try (Jedis redisCache = Redis.get()) {
                // 授权成功后,
                // 清理用户详情缓存...
                redisCache.hdel(
                        RedisKeyEnum.KEY_USER_INFO.format(userId),
                        Constant.USER_DETAIL
                );
            }

            // 通过用户ID查询用户详情

            LoginResult result = new LoginResult();
            result.setUserId(userId);
            // 设置用户信息
            result.setUserName("");
            result.setTicket(Ticket.getTicket(userId));
            Out<Long> outUkeyExpireAt = new Out<>();
            result.setUkey(UKey.get(userId, outUkeyExpireAt));
            result.setUkeyExpireAt(outUkeyExpireAt.getVal());

            builder.setData(result);
        }
    }
}
