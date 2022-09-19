package top.zopx.goku.test.material;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.zopx.goku.framework.material.constant.MaterialPreCons;
import top.zopx.goku.framework.material.entity.MaterialPreDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.material.entity.vo.MaterialPreVO;
import top.zopx.goku.framework.material.factory.ServiceFactory;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.testGoku.GokuTest;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

@SpringBootTest(classes = GokuTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialTest {

    @Resource
    private ServiceFactory serviceFactory;

    @Test
    public void serviceTest() {
        List<IMaterialService> serviceList = serviceFactory.getServiceList();
        Assert.assertEquals(serviceList.size(), 1);
    }

    @Test
    public void existsBucket() {
        IMaterialService materialService = serviceFactory.get();
        boolean security = materialService.existsBucket(new BucketName("security"));

        Assert.assertTrue("bucket is exists?", security);
    }

    @Test
    public void uploadPre() {
        IMaterialService materialService = serviceFactory.get();

        MaterialPreDTO preDTO = new MaterialPreDTO(
                new BucketName("security"),
                new ObjectName("11/22/security_1657874050226111.jpg"),
                MaterialPreCons.DIRECT_UPLOAD,
                Duration.ofHours(1L)
        );
        MaterialPreVO materialPreVO = materialService.uploadPre(preDTO);
        System.out.println(materialPreVO.getHost() + "  " + materialPreVO.getDir());

    }
}
