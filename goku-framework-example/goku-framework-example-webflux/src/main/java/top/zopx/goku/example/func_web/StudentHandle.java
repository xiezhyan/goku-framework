package top.zopx.goku.example.func_web;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.zopx.goku.example.func_web.entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/04 20:03
 */
@Service
public class StudentHandle {

    private static final Map<Long, Student> DB_MAP = new HashMap<>();

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Student.class)
                .doOnNext(student ->
                        DB_MAP.put(student.getId(), student)
                )
                .flatMap(student -> ServerResponse.ok().bodyValue("OK"));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
//        Long id = Long.valueOf(request.queryParam("id").orElseThrow(() -> new RuntimeException("id is null")));
        Long id = Long.valueOf(Optional.ofNullable(request.pathVariable("id")).orElseThrow(() -> new RuntimeException("id is null")));
        return Mono.just(DB_MAP.get(id))
                .flatMap(
                        student -> ServerResponse.ok().bodyValue(student)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> page(ServerRequest request) {
        return Mono.just(new ArrayList<>(DB_MAP.values()))
                .flatMap(students -> ServerResponse.ok().bodyValue(students))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
