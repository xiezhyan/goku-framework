package top.zopx.goku.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.goku.framework.redis.lock.Locked;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * 角色(Role)表控制层
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@RestController
@RequestMapping("/lock")
public class LockController {

    @Locked
    @GetMapping("/test")
    public R<Boolean> test() {
        return R.status(true);
    }


    @Locked(key = "#id")
    @GetMapping("/test1")
    public R<Boolean> test1(@RequestParam("id") String id) {
        return R.status(true);
    }
}

