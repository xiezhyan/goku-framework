package top.zopx.goku.example.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.zopx.goku.example.func_web.entity.Student;
import top.zopx.goku.example.service.IStudentService;

import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/04 20:03
 */
@SuppressWarnings("all")
@Service
public class StudentServiceImpl implements IStudentService {

    private static final Map<Long, Student> DB_MAP = new HashMap<>();

    @Override
    public Mono<Student> getById(Long id) {
        return Mono.justOrEmpty(DB_MAP.get(id));
    }

    @Override
    public Mono<Boolean> save(Student student) {
        return Mono.create(sink -> {
            try {
                DB_MAP.put(student.getId(), student);
                sink.success(true);
            } catch (Exception e) {
                sink.error(e);
            }
        });
//        return Mono.defer(new Supplier<Mono<Boolean>>() {
//            @Override
//            public Mono<Boolean> get() {
//                DB_MAP.put(student.getId(), student);
//                return Mono.just(true);
//            }
//        });
    }

    @Override
    public Flux<Student> getList() {
        return Flux.fromIterable(DB_MAP.values());
    }
}
