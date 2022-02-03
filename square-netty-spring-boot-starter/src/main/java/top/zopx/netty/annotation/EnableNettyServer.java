package top.zopx.netty.annotation;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.zopx.netty.configurator.properties.NettyProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以开启加载Netty服务的配置
 * 示例
 * @Component
 * public class ApplicationInitialContext implements CommandLineRunner {
 *
 *     @Resource
 *     private NettyProperties nettyProperties;
 *     @Resource
 *     private ApplicationContext applicationContext;
 *
 *     @Bean(destroyMethod = "destory")
 *     public NettyServerAcceptor nettyServerAccepter() {
 *         return new NettyServerAcceptor.Builder()
 *                 .setApp(nettyProperties.getApp())
 *                 .setWebs(nettyProperties.getWebs())
 *                 .setWriteTimeout(nettyProperties.getWriteTimeout())
 *                 .setReadTimeout(nettyProperties.getReadTimeout())
 *                 .setFactory(new BaseChannelHandlerFactory() {
 *                     @Override
 *                     public ChannelHandler createAppMsgHandler() {
 *                         return new ChannelDuplexHandler() {
 *                             @Override
 *                             public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
 *
 *                             }
 *
 *                             @Override
 *                             public void read(ChannelHandlerContext ctx) throws Exception {
 *                                 super.read(ctx);
 *                             }
 *
 *                         };
 *                     }
 *
 *                     @Override
 *                     public ChannelHandler createWSMsgHandler() {
 *                         return null;
 *                     }
 *                 })
 *                 .build();
 *     }
 *
 *     @Override
 *     public void run(String... args) throws Exception {
 *         applicationContext.getBean(NettyServerAcceptor.class).bind();
 *     }
 * }
 *
 *
 * @author 俗世游子
 * @date 2021/10/3
 * @email xiezhyan@126.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableConfigurationProperties({
        NettyProperties.class
})
public @interface EnableNettyServer {
}
