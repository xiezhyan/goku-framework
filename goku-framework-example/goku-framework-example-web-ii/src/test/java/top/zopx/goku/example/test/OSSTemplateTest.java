package top.zopx.goku.example.test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.zopx.goku.example.ExampleApp;
import top.zopx.goku.framework.oss.service.OSSTemplate;
import top.zopx.goku.framework.tools.util.json.GsonUtil;
import top.zopx.goku.framework.http.util.log.LogHelper;

import java.io.IOException;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/22
 */
@SpringBootTest(classes = ExampleApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OSSTemplateTest {

    @Resource
    private OSSTemplate ossTemplate;
    @Resource
    private AmazonS3 amazonS3;

    private static final String BUCKET_NAME = "oss-test";

    @Test
    public void createBucket() {
        Boolean created = ossTemplate.createBucket(BUCKET_NAME);
        Assert.assertTrue(created);
    }

    @Test
    public void deleteBucket() {
        Boolean deleted = ossTemplate.deleteBucket(BUCKET_NAME);
        Assert.assertTrue(deleted);
    }

    @Test
    public void createBucketToAcl() {
        CreateBucketRequest request = new CreateBucketRequest(BUCKET_NAME);
        request.setCannedAcl(CannedAccessControlList.Private);
        Boolean deleted = ossTemplate.createBucket(request);
        Assert.assertTrue(deleted);
    }

    @Test
    public void putObject() {
        ossTemplate.putObject(BUCKET_NAME, "/2023/04/01/abc", "abc");
    }

    @Test
    public void getObject() throws IOException {
        S3Object object = ossTemplate.getObject(BUCKET_NAME, "/2023/04/01/abc");
        byte[] buf = new byte[1024];
        int len = 0;
        StringBuffer sb = new StringBuffer();
        while ((len = object.getObjectContent().read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }
        LogHelper.getLogger(getClass())
                .info(
                        "{},{},{},{}",
                        object.getBucketName(), object.getKey(),
                        GsonUtil.getInstance().toJson(object.getObjectMetadata()),
                        sb.toString()
                );
    }
}
