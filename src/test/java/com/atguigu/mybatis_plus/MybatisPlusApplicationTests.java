package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.entity.User;
import com.atguigu.mybatis_plus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisPlusApplicationTests {

    /**
     * IDEA在 userMapper 处报错，因为找不到注入的对象，因为类是动态创建的，但是程序可以正确的执行。
     *
     * 为了避免报错，可以在 dao 层 的接口上添加 @Repository 注
     */
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testQuery() {

        List<User> users = this.userMapper.selectList(null);

        users.forEach(System.out::println);

    }

}
