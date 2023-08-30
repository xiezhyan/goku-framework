package top.zopx.goku.framework.http.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.http.constant.ErrorEnum;
import top.zopx.goku.framework.http.context.SpringContext;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.pass.codec.base64.Base64Util;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/27 09:38
 */
@SuppressWarnings("all")
public class Jwt {

    private final String DATA = "DATA";

    private final BootstrapToken bootstrapToken;

    private Algorithm algorithm;

    public Jwt() {
        this(SpringContext.getBean(BootstrapToken.class));
    }

    public Jwt(BootstrapToken bootstrapToken) {
        this.bootstrapToken = bootstrapToken;
        try {
            algorithm = Algorithm.RSA512(
                    getPublicKey(Base64Util.INSTANCE.decode(bootstrapToken.getPublicKey())),
                    getPrivateKey(Base64Util.INSTANCE.decode(bootstrapToken.getPrivateKey()))
            );
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }


    /**
     * 生成Token
     *
     * @param claim 动态参数
     * @return token
     */
    public String genericToken(Map<String, Object> claim) {
        return genericToken(claim, bootstrapToken.getExpireTime());
    }

    /**
     * 生成refreshToken
     *
     * @param claim 动态参数
     * @return token
     */
    public String genericRefreshToken(Map<String, Object> claim) {
        return genericToken(claim, bootstrapToken.getRefreshExpireTime());
    }

    /**
     * 生成token
     *
     * @param claim      动态参数
     * @param expireTime 有效期
     * @return token
     */
    public String genericToken(Map<String, Object> claim, Duration expireTime) {
        return JWT.create()
                .withIssuer(bootstrapToken.getIssue())
                .withSubject(bootstrapToken.getSubject())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + expireTime.toMillis())
                )
                .withIssuedAt(new Date())
                .withClaim(DATA, claim)
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    /**
     * JWT校验
     *
     * @param token token
     */
    public void check(String token) {
        if (StringUtils.isBlank(token)) {
            throw new BusException(ErrorEnum.TOKEN_NOT_ERROR);
        }
        try {
            JWT.require(algorithm).build().verify(token);
        } catch (Exception e) {
            throw new BusException(ErrorEnum.TOKEN_EXISTS);
        }
    }

    /**
     * 从JWT token中获取到传递的参数
     *
     * @param token token
     * @return 参数
     */
    public Map<String, Object> get(String token) {
        check(token);
        return JWT.decode(token).getClaim(DATA).asMap();
    }

    /**
     * 从Token中获取过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public Date getExpiredDateFromToken(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    static RSAPublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) factory.generatePublic(spec);
    }

    static RSAPrivateKey getPrivateKey(byte[] bytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }


}
