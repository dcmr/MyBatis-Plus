package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.entity.User;
import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eternity
 * @create 2019-11-19 19:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    /**
     *  deleteById 根据id删除记录
     */
    @Test
    public void testDeleteById(){

        int result = userMapper.deleteById(6L);
        System.out.println(result);
    }

    /**
     * deleteBatchIds  批量删除
     */
    @Test
    public void testDeleteBatchIds() {

        int result = userMapper.deleteBatchIds(Arrays.asList(8, 9, 10));
        System.out.println(result);
    }

    /**
     * deleteByMap 简单条件删除
     */
    @Test
    public void testDeleteByMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);

        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    /**
     * 1、物理删除和逻辑删除
     * 物理删除：真实删除，将对应数据从数据库中删除，之后查询不到此条被删除数据
     * 逻辑删除：假删除，将对应数据中代表是否被删除字段状态修改为“被删除状态”，之后在数据库中仍旧能看到此条数据记录
     */

    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {

        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }

    /**
     * 测试 逻辑删除后的查询：
     * 不包括被逻辑删除的记录
     * 打印的sql语句，包含 WHERE deleted=0
     */
    @Test
    public void testLogicDeleteSelect() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    /**
     * 测试 性能分析插件
     */
    @Test
    public void testPerformance() {
        User user = new User();
        user.setName("我是Helen");
        user.setEmail("helen@sina.com");
        user.setAge(18);
        userMapper.insert(user);
    }
}

