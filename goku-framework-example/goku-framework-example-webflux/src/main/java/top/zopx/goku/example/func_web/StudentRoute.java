package top.zopx.goku.example.func_web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/12/04 20:10
 */
@Configuration
public class StudentRoute {

    @Bean
    public RouterFunction<ServerResponse> routes(StudentHandle handler) {
        return RouterFunctions.nest(
                RequestPredicates.path("student"),
                RouterFunctions.nest(
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        RouterFunctions.route(
                                RequestPredicates.POST(""), handler::save
                        ).andRoute(
                                RequestPredicates.GET("/{id}"),
                                handler::getById
                        ).andRoute(
                                RequestPredicates.GET("page"), handler::page
                        )
                )
        );
    }

}
