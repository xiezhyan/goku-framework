package top.zopx.netty.annotation;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.zopx.netty.configurator.NettyProperties;

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
 *                 .setListener(new ChannelInboundHandlerListener() {
 *                     @Override
 *                     public void doActive(ChannelHandlerContext ctx) {
 *                         // 连上的处理方式
 *                     }
 *
 *                     @Override
 *                     public void doRead(ChannelHandlerContext ctx, Object msg) {
 *                         System.out.println(msg.toString());
 *                         // 1、根据msg的class类型获取执行 ICmdHandler
 *                         // 2、处理指令
 *                     }
 *
 *                     @Override
 *                     public void doInactive(ChannelHandlerContext ctx) {
 *                         // 断开的处理方式
 *                     }
 *                 })
 *                 .setAppCodec(new ByteToMessageCodec<GeneratedMessageV3>() {
 *                     @Override
 *                     protected void encode(ChannelHandlerContext channelHandlerContext, GeneratedMessageV3 generatedMessageV3, ByteBuf byteBuf) throws Exception {
 *                         // 自定义协议编码器
 *                     }
 *
 *                     @Override
 *                     protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
 *                         // 自定义协议解码器
 *                     }
 *                 })
 *                 .setWebsCodec(new MessageToMessageCodec<BinaryWebSocketFrame, GeneratedMessageV3>() {
 *                     @Override
 *                     protected void encode(ChannelHandlerContext channelHandlerContext, GeneratedMessageV3 generatedMessageV3, List<Object> list) throws Exception {
 *                         // websocket 解码器
 *                     }
 *
 *                     @Override
 *                     protected void decode(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame, List<Object> list) throws Exception {
 *                         // websocket编码器 out.add(new BinaryWebSocketFrame(buff));
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
