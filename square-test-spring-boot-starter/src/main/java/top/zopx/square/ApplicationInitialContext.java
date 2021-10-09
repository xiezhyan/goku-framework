package top.zopx.square;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zopx.netty.configurator.NettyProperties;
import top.zopx.netty.parse.NettyServerAcceptor;

import javax.annotation.Resource;
import java.util.List;

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
                .setListener((ctx, msg) -> {
                    System.out.println(msg.toString());
                })
                .setAppCodec(new ByteToMessageCodec<GeneratedMessageV3>() {
                    @Override
                    protected void encode(ChannelHandlerContext channelHandlerContext, GeneratedMessageV3 generatedMessageV3, ByteBuf byteBuf) throws Exception {

                    }

                    @Override
                    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

                    }
                })
                .setWebsCodec(new MessageToMessageCodec<BinaryWebSocketFrame, GeneratedMessageV3>() {
                    @Override
                    protected void encode(ChannelHandlerContext channelHandlerContext, GeneratedMessageV3 generatedMessageV3, List<Object> list) throws Exception {

                    }

                    @Override
                    protected void decode(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame, List<Object> list) throws Exception {

                    }
                })
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        applicationContext.getBean(NettyServerAcceptor.class).bind();
    }
}
