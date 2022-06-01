package top.zopx.square;

import io.minio.MinioClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.Resource;

/**
 * @author 俗世游子
 * @date 2021/4/14
 */
@SpringBootApplication
public class TestApplication {

    @Resource
    private MinioClient writeMinioClient;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Cacheable

    public void test() {

    }
}
