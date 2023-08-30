package top.zopx.goku.example.socket.test;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.framework.tools.pass.codec.base64.Base64Util;

/**
 * 异步执行
 *
 * @author Mr.Xie
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public class ByteUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteUtil.class);
    private static final  PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;


    public static String getByteData(GeneratedMessageV3 messageV3) {
        ByteBuf buffer = allocator.buffer();

        byte[] bytes = messageV3.toByteArray();

        buffer.writeShort(2 + bytes.length);
        buffer.writeShort(MsgRecognizer.getInstance().getCodeByMsgObj(messageV3.getClass()));
        buffer.writeBytes(bytes);

        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        return get(data);
    }

    private static String get(byte[] data) {
        return Base64Util.INSTANCE.encode(data);
    }
}
