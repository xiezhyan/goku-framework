package top.zopx.goku.example.controller;

import org.apache.commons.lang3.RandomUtils;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.zopx.goku.example.func_web.entity.Student;
import top.zopx.goku.example.service.IStudentService;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/04 20:03
 */
@RestController
@RequestMapping("/rest/student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    @GetMapping("detail")
    public Mono<Student> getById(@RequestParam("id") Long id) {
        return studentService.getById(id);
    }

    @GetMapping("page")
    public Flux<Student> getList() {
        return studentService.getList();
    }

    @PostMapping("save")
    public Mono<Boolean> save(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping(value = "sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse() {
        Flux<String> flux =
                Flux.fromArray(new String[]{"javaboy", "itboyhub", "www.javaboy.org", "itboyhub.com"})
                        .map(s -> {
                            return "my->data->" + s;
                        }).delayElements(Duration.ofMillis(500));
        return flux;
    }
}
