package top.zopx.goku.framework.support.mysql.binlog.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.client.BinlogClient;

import jakarta.annotation.Resource;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:50
 */
@Component
public class BinlogRunner implements CommandLineRunner {

    @Resource
    private BinlogClient binlogClient;

    @Override
    public void run(String... args) throws Exception {
        binlogClient.connect();
    }

}
