package top.zopx.square.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 俗世游子
 * @date 2021/4/14
 */
public class User {

    private Long id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "age不能为空")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
