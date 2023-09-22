package top.zopx.goku.framework.mybatis.configuration;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/17
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        // 开启驼峰标识
        return configuration -> configuration.setMapUnderscoreToCamelCase(true);
    }

}
