package top.zopx.goku.example.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.zopx.goku.example.func_web.entity.Student;

import java.util.List;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/04 20:03
 */
@SuppressWarnings("all")
public interface IStudentService {

    Mono<Student> getById(Long id);

    Mono<Boolean> save(Student student);

    Flux<Student> getList();
}
