package top.zopx.starter.tools.constants;

/**
 * version: Redis Key
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/27
 */
public class Redis {

    public static class RedisKey {
        /**
         * 用户登录
         */
        static final String MEMBER_TOKEN = "USER:TOKEN:%s";

        /**
         * 验证码
         */
        static final String SMS_CODE_KEY = "SMS:CODE:%s:%s";

        /***
         *  url 拦截
         */
        static final String CHECK_IP_KEY = "CHECK:INCR:%s:%s";

        /**
         * ip黑名单
         */
        public static final String BLOCK_IP_SET = "BLOCK_IP_SET";

        /***
         *  菜单权限
         */
        static final String MEMBER_TREE_PERMISSION = "PERMISSION:TREE:%s";

        /**
         * 按钮权限
         */
        static final String MEMBER_BUTTON_PERMISSION = "PERMISSION:BUTTON:%s";
    }

    public static class ReplaceKey {
        /**
         * 用户登录key
         *
         * @param token token
         * @return String
         */
        public static String getTokenKey(String token) {

            return  String.format(RedisKey.MEMBER_TOKEN, token);
        }

        /**
         * 手机验证码
         *
         * @param tel 手机号
         * @return String
         */
        public static String getSmsCodeKey(String model, String tel) {
            return String.format(RedisKey.SMS_CODE_KEY, model, tel);
        }

        /**
         * 统计该ip访问url的时长
         *
         * @param ip  ip
         * @param url url
         * @return String
         */
        public static String getCheckIpKey(String ip, String url) {
            return String.format(RedisKey.CHECK_IP_KEY, ip, url);
        }

        /**
         * 获取用户菜单权限
         *
         * @param memberId 会员ID
         * @return String
         */
        public static String getTreePermissionKey(String memberId) {

            return String.format(RedisKey.MEMBER_TREE_PERMISSION, memberId);
        }

        /**
         * 获取用户接口权限
         *
         * @param memberId 会员ID
         * @return String
         */
        public static String getButtonPermissionKey(String memberId) {

            return String.format(RedisKey.MEMBER_BUTTON_PERMISSION, memberId);
        }
    }

}
