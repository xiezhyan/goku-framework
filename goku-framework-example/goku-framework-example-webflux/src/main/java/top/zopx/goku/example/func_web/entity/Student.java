package top.zopx.goku.example.func_web.entity;

import java.io.Serializable;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/04 20:04
 */
public class Student implements Serializable {

    private Long id;

    private String name;

    public Student() {
    }

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
