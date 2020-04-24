package top.zopx.starter.upload.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.starter.upload.auto.UploadAutoConfig;

import java.lang.annotation.*;

/**
 * top.zopx.starter.upload.annotation.EnableUploadFile
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({UploadAutoConfig.class})
public @interface EnableUploadFile {
}
