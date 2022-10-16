package top.zopx.goku.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.zopx.testGoku.GokuTest;
import top.zopx.testGoku.entity.User;
import top.zopx.testGoku.entity.dto.UserDTO;
import top.zopx.testGoku.mapper.UserMapper;
import top.zopx.testGoku.service.UserService;

import javax.annotation.Resource;

@SpringBootTest(classes = GokuTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    @Test
    public void updateById() {
        User entity = new User();
        entity.setId(1L);
        entity.setAppId(1L);
        userMapper.updateById(entity);
    }

    @Test
    public void updateByPriKey() {
        UserDTO body = new UserDTO();
        body.setNickName("11111");
        userService.updateByPriKey(body, 1L);
    }
}