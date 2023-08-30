package top.zopx.goku.framework.redis.bus.code;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import top.zopx.goku.framework.redis.bus.constant.CodeTypeEnum;
import top.zopx.goku.framework.redis.bus.constant.RedisKeyEnum;
import top.zopx.goku.framework.redis.bus.properties.BootstrapCode;
import top.zopx.goku.framework.redis.core.WriteRedisTemplate;
import top.zopx.goku.framework.tools.exception.BusAsserts;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.id.CodeUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/7 21:10
 */
@Service("codeService")
public class CodeServiceImpl implements CodeService{

    @Resource
    private BootstrapCode bootstrapCode;
    @Resource
    private WriteRedisTemplate writeRedisTemplate;

    @Override
    public CodeVO genericCode(CodeTypeEnum type, String key) {
        Integer countLength = bootstrapCode.getCountLength();
        String redisKey = RedisKeyEnum.KEY_CODE_GENERIC.format(key);
        String imgText = "";
        String resultCode = switch (type) {
            case NUMBER -> CodeUtil.genericNumCode(countLength);
            case CHARACTOR -> CodeUtil.genericOpeCode(bootstrapCode.getCharactor(), countLength);
            case ALPH -> CodeUtil.genericAlphCode(countLength);
            case COMPUTE -> {
                int x = RandomUtils.nextInt(51,99);
                int y = RandomUtils.nextInt(1,50);
                yield switch (RandomUtils.nextInt(1,4)) {
                    case 1 -> {
                        // +
                        imgText = String.format("%d+%d=", x, y);
                        yield String.valueOf(x + y);
                    }
                    case 2 -> {
                        // -
                        imgText = String.format("%d-%d=", x, y);
                        yield String.valueOf(x - y);
                    }
                    case 3 -> {
                        // *
                        imgText = String.format("%dx%d=", x, y);
                        yield String.valueOf(x * y);
                    }
                    default -> throw new BusException("", IBus.ERROR_CODE);
                };
            }
        };

        writeRedisTemplate.opsForValue()
                .set(redisKey, resultCode, bootstrapCode.getExpireTime().getSeconds(), TimeUnit.SECONDS);

        return new CodeVO(imgText, resultCode);
    }

    @Override
    public void check(String key, String code) {
        String redisKey = RedisKeyEnum.KEY_CODE_GENERIC.format(key);

        Serializable cacheCode = writeRedisTemplate.opsForValue()
                .get(redisKey);

        BusAsserts.isNull(cacheCode, "bus.cache.isnull");
        BusAsserts.isTrue(Objects.equals(code, Optional.ofNullable(cacheCode).orElse("").toString()), "bus.cache.notEquals");

        writeRedisTemplate.delete(redisKey);
    }
}
