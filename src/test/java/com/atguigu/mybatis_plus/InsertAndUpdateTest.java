package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.entity.User;
import com.atguigu.mybatis_plus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author eternity
 * @create 2019-11-19 18:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class InsertAndUpdateTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 插入数据
     */
    @Test
    public void testInsert(){

        User user = new User();
        user.setName("Helen");
        user.setAge(18);
        user.setEmail("55317332@qq.com");

        int result = userMapper.insert(user);
        log.info("影响的行数：" + result); //影响的行数
        log.info("id：" + user); //id自动回填
    }

    /**
     * 修改数据
     */
    @Test
    public void testUpdateById(){

        User user = new User();
        user.setId(1L);
        user.setAge(28);

        int result = userMapper.updateById(user);
        log.info("影响的行数：" + result);
    }

    /**
     * 测试：乐观锁插件更新时自动更新version的值
     */
    @Test
    public void testOptimisticLocker() {

        //1、查询数据
        User user1 = userMapper.selectById(1L);
        //2、修改数据
        user1.setViewCount(user1.getViewCount() + 1);

        //3、执行更新：当没有并发用户修改时，更新成功并将version的值 +1
        userMapper.updateById(user1);
    }

    /**
     * 场景：并发修改
     *     当A用户根据ID获取了数据更新浏览数，同时B用户也获取了数据更新浏览数；
     *     如果A用户修改成功，那么B用户是否也能修改成功？
     *
     * 结论：此时两个用户查询了数据，view_count的值应该+2，
     *      但是测试结果只加了1，并报告两条记录都更新成功，因此并发操作数据不一致
     */
    @Test
    /*synchronized 悲观锁*/ public void testConcurrentUpdate() {

        //1、查询数据
        User user1 = userMapper.selectById(7L);
        //2、修改数据
        user1.setViewCount(user1.getViewCount() + 1);

        //模拟另一个用户中间也进行了数据查询和更新操作
        User user2 = userMapper.selectById(7L);
        user2.setViewCount(user2.getViewCount() + 1);
        int result2 = userMapper.updateById(user2);
        log.info(result2 > 0 ? "user2更新成功" : "user2更新失败");

        //3、执行更新
        int result1 = userMapper.updateById(user1);
        log.info(result1 > 0 ? "user1更新成功" : "user1更新失败");
    }

    /**
     * 并发访问中的数据不一致的解决方案：
     * 1、加锁：悲观锁，适合并发量不太大，但是写多的情况
     * 2、引入缓存：redis、定时做数据同步，适合高并发
     * 3、乐观锁：对数据库增加一个字段version
     *     读数据时读取version = 1
     *     修改数据时版本号 +1   where version = 1
     *     适合场景，适合并发量不太发，写少读多的情况
     */

}
