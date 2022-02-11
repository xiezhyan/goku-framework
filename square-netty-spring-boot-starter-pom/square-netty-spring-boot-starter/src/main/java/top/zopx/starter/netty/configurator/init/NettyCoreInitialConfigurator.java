package top.zopx.starter.netty.configurator.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import top.zopx.square.netty.configurator.parse.NettyServerAcceptor;

import javax.annotation.Resource;

/**
 * @author 俗世游子
 * @date 2022/2/11 0011
 * @email xiezhyan@126.com
 */
public class NettyCoreInitialConfigurator implements CommandLineRunner {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        applicationContext.getBean(NettyServerAcceptor.class).start();
    }
}
