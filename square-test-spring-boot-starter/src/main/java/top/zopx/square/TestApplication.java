package top.zopx.square;

import io.minio.MinioClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.zopx.goku.framework.support.primary.annotation.EnableDistributedMySQLPrimary;
import top.zopx.goku.framework.support.primary.annotation.EnableDistributedZookeeperPrimary;

import javax.annotation.Resource;

/**
 * @author 俗世游子
 * @date 2021/4/14
 */
@SpringBootApplication
@EnableDistributedMySQLPrimary
@EnableDistributedZookeeperPrimary
public class TestApplication {

    @Resource
    private MinioClient writeMinioClient;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
