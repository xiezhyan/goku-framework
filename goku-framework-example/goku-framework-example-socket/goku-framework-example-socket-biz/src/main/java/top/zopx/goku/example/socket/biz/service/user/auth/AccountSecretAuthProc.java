package top.zopx.goku.example.socket.biz.service.user.auth;

import org.apache.ibatis.session.SqlSession;
import top.zopx.goku.example.socket.biz.dao.IUserDao;
import top.zopx.goku.framework.biz.dao.MybatisDao;
import top.zopx.goku.framework.tools.digest.crc32.Crc32Util;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.io.Serializable;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public final class AccountSecretAuthProc implements IAuthProc {

    private final AccountSecret accountSecret;

    public AccountSecretAuthProc(String login) {
        accountSecret = JsonUtil.getInstance().toObject(login, AccountSecret.class);
    }

    @Override
    public int getAsyncOpId() {
        return StringUtil.toInteger(
                Crc32Util.INSTANCE.encode("", accountSecret.getUsername())
        );
    }

    @Override
    public String getTempId() {
        return "A_S_" + accountSecret.getUsername();
    }

    @Override
    public long doAuth() {
        // 存在就返回
        // 不存在，创建新用户，然后返回
        try (SqlSession session = MybatisDao.openBizDbSession()) {
            final IUserDao userDao = session.getMapper(IUserDao.class);
        }
        return 1;
    }


    private static class AccountSecret implements Serializable {
        private String username;

        private String password;

        private String clientIp;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }
    }
}
