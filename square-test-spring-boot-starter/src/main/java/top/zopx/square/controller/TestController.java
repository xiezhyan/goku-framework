package top.zopx.square.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.starter.log.annotations.OperatorLogAnnotation;

import javax.validation.constraints.NotBlank;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@RestController
@RequestMapping("/test")
@Validated
public class TestController {

    @OperatorLogAnnotation(value = "请求名称")
    @GetMapping("/get")
    public String get(@NotBlank(message = "name不能为空") String name) {
        return ":hello:" + name;
    }


}
