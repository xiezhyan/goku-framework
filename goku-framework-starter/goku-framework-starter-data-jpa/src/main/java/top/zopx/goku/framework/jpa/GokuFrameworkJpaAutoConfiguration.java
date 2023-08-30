package top.zopx.goku.framework.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import top.zopx.goku.framework.tools.util.id.SnowFlake;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/1 14:02
 */
@ComponentScan(value = "top.zopx.goku.framework.jpa")
public class GokuFrameworkJpaAutoConfiguration {

    @Value("${spring.jpa.id.snowflake.datacenter-id:1}")
    private Long datacenterId;
    @Value("${spring.jpa.id.snowflake.machine-id:1}")
    private Long machineId;

   @Bean
   public SnowFlake snowFlake() {
       return new SnowFlake(datacenterId, machineId);
   }

}
