package top.zopx.goku.framework.jpa.strategy;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.spi.Configurable;
import top.zopx.goku.framework.tools.util.id.SnowFlake;
import top.zopx.goku.framework.http.context.SpringContext;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/3
 */
public class SnowflakeStrategy implements IdentifierGenerator, Configurable {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return SpringContext.getBean(SnowFlake.class).nextId();
    }

    @Override
    public void configure(Map<String, Object> map) {
        // Nothing
    }
}
