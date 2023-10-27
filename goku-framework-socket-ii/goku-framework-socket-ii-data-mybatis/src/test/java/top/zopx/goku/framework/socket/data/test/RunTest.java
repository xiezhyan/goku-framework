package top.zopx.goku.framework.socket.data.test;

import org.junit.Test;
import top.zopx.goku.framework.socket.data.mybatis.handle.MyBatisDaoConfigureInitRequestHandler;
import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.tools.circuit.chain.RequestHandler;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 20:34
 */
public class RunTest {

    @Test
    public void doTest() {
        // nothing
    }

    public static void main(String[] args) {
        Context context = new Context(args);

        context.add(new MyBatisDaoConfigureInitRequestHandler(RunTest.class));
        context.add(new RequestHandler() {
            @Override
            public void handleRequest(Context context) {
                // etcd
            }
        });
        context.execute();
    }

}
