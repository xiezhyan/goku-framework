package top.zopx.goku.example.controller;

import org.apache.commons.lang3.RandomUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zopx.goku.framework.material.constant.MaterialPreCons;
import top.zopx.goku.framework.material.entity.MaterialPreDTO;
import top.zopx.goku.framework.material.entity.UploadDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.material.entity.vo.MaterialPreVO;
import top.zopx.goku.framework.material.entity.vo.UploadVO;
import top.zopx.goku.framework.material.factory.ServiceFactory;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.material.util.ObjectNameUtil;
import top.zopx.goku.framework.tools.digest.rsa.RSAUtil;
import top.zopx.goku.framework.tools.digest.rsa.RsaKey;
import top.zopx.goku.framework.tools.digest.sm2.SM2Util;
import top.zopx.goku.framework.tools.digest.sm3.SM3Util;
import top.zopx.goku.framework.tools.digest.sm4.SM4Util;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.tools.exceptions.BusException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * minio文件上传
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Resource
    private ServiceFactory serviceFactory;

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @PostMapping("/basicFile")
    public R<List<UploadVO>> uploadBasicFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (Objects.isNull(file)) {
            throw new BusException("上传文件为空", 400, "isnull.upload.file");
        }

        String pathObject = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

//        serviceFactory.get().upload();   materialServicesList.get(0)默认会取配置的第一项
        List<UploadVO> uploadVOList = serviceFactory.getMaterialServiceMap().get(IMaterialService.MINIO_SERVER)
                .upload(
                        Collections.singletonList(
                                UploadDTO.create()
                                        // bucket的名称
                                        .setBucketName(new BucketName("testminio"))
                                        // 文件
                                        .setBody(file.getBytes())
                                        // 文件原始名称
                                        .setOriginalFilename(file.getOriginalFilename())
                                        // 指定路径
                                        .setPathObject(pathObject)
                                        // 文件大小
                                        .setSize(file.getSize())
                                        // 文件类型
                                        .setContentType(file.getContentType())
                                        .build()
                        )
                );
        return R.result(uploadVOList);
    }

    /**
     * 文件直传
     *
     * @param data 文件名称，不需要携带目录，必须有文件格式
     * @return 直传连接
     */
    @PostMapping("/uploadPre")
    public R<Map<String, String>> uploadPre(@RequestParam("data") String data)  {
        String fileName = data;
        if (!fileName.contains(".")) {
            throw new BusException("未知格式的文件", 400, "unknown.file");
        }
        String pathObject = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String objectName = String.format("%s/%s", pathObject, ObjectNameUtil.getNewFileName(fileName, ""));
        MaterialPreVO preVO = serviceFactory.getMaterialServiceMap().get(IMaterialService.MINIO_SERVER).uploadPre(
                new MaterialPreDTO(
                        new BucketName("testminio"),
                        new ObjectName(objectName),
                        MaterialPreCons.DIRECT_UPLOAD,
                        Duration.ofDays(1L)
                )
        );

        // http://192.168.86.200:9001/testminio/2022/12/28/B4BD5C50A7284266B287F049EC706682.doc?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioAdmin%2F20221228%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221228T034932Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=a2c96e2b34186252da66f2caaa41bf6c79777260a3abfbc168771ad8112e7deb
        return R.result(
                new HashMap<String, String>(4) {{
                    put("host", preVO.getHost());
                    put("objectName", objectName);
                }}
        );
    }

    /**
     * 获取查看连接
     *
     * @param objectName  对象名称
     * @param expireAtHour 过期时间
     * @return 链接
     */
    @GetMapping("/showPreFile")
    public R<String> getPre(
            @RequestParam(value = "objectName") String objectName,
            @RequestParam("expireAtHour") Long expireAtHour
    ) {

        MaterialPreVO preVO = serviceFactory.getMaterialServiceMap().get(IMaterialService.MINIO_SERVER).uploadPre(
                new MaterialPreDTO(
                        new BucketName("testminio"),
                        new ObjectName(objectName),
                        MaterialPreCons.GET,
                        Duration.ofHours(expireAtHour)
                )
        );
        // minio 指定小时内： 减去随机时间，防止雪崩，防止过期
        return R.result(preVO.getHost());
    }

    /**
     * 移除文件
     *
     * @return 是否移除
     */
    @DeleteMapping("/remove")
    public R<Boolean> removeByKey(@RequestParam("data") String objectName) {
        serviceFactory.getMaterialServiceMap().get(IMaterialService.MINIO_SERVER).remove(
                new BucketName("testminio"),
                new ObjectName(objectName)
        );
        return R.result(true);
    }
}

