package top.zopx.starter.step.up.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.starter.step.up.auto.StepCodeAutoConfig;

import java.lang.annotation.*;

/**
 * top.zopx.starter.step.up.annotation.EnableStepUpCode
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({StepCodeAutoConfig.class})
public @interface EnableStepUpCode {
}
