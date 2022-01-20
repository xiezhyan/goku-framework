package top.zopx.square;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zopx.netty.configurator.NettyProperties;
import top.zopx.netty.parse.NettyServerAcceptor;
import top.zopx.square.netty.ChannelHandlerFactoryImpl_0;

import javax.annotation.Resource;

/**
 * 启动程序
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/10
 */
@Component
public class ApplicationInitialContext implements CommandLineRunner {

    @Resource
    private NettyProperties nettyProperties;
    @Resource
    private ApplicationContext applicationContext;

    @Bean(destroyMethod = "destory")
    public NettyServerAcceptor nettyServerAccepter() {
        return new NettyServerAcceptor.Builder()
                .setApp(nettyProperties.getApp())
                .setWebs(nettyProperties.getWebs())
                .setWriteTimeout(nettyProperties.getWriteTimeout())
                .setReadTimeout(nettyProperties.getReadTimeout())
                .setFactory(new ChannelHandlerFactoryImpl_0())
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        applicationContext.getBean(NettyServerAcceptor.class).bind();
    }
}
