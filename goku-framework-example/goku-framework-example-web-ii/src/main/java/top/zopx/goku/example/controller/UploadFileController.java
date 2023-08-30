package top.zopx.goku.example.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.annotation.Resource;
import org.assertj.core.util.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.goku.framework.http.context.GlobalContext;
import top.zopx.goku.framework.redis.bus.code.CodeService;
import top.zopx.goku.framework.redis.bus.code.CodeVO;
import top.zopx.goku.framework.redis.bus.constant.CodeTypeEnum;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.tools.pass.codec.base64.Base64Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * minio文件上传
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {


    @Resource
    private DefaultKaptcha producer;
    @Resource
    private CodeService codeService;

    @GetMapping("/img_code")
    public R<Map<String, Object>> genericImgCode() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        GlobalContext.CurrentResponse.set("sessionId", sessionId);
        //生成文字验证码
        CodeVO resultCodeVO = codeService.genericCode(CodeTypeEnum.COMPUTE, sessionId);
        //生成文字对应的图片验证码
        BufferedImage image = producer.createImage(resultCodeVO.getImgText());
        //将图片写出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        //对写出的字节数组进行Base64编码 ==> 用于传递8比特字节码
        return R.result(
                Maps.newHashMap("img", Base64Util.INSTANCE.encode(outputStream.toByteArray()))
        );
    }

    @GetMapping("/img_check")
    public void checkImgCode(String key, String code) {
        codeService.check(key, code);
    }
}

