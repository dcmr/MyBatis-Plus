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
public class QueryTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过多个id批量查询
     */
    @Test
    public void testSelectBatchIds(){

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    /**
     * 简单的条件查询
     */
    @Test
    public void testSelectByMap(){
        //map中的key对应数据库中的列名。如：数据库user_id，实体类是userId，这时map的key需要填写user_id
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);

        users.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void testSelectPage() {

        Page<User> page = new Page<>(2,3);
        userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    /**
     * 当指定了特定的查询列时，希望分页结果列表只返回被查询的列，而不是很多null值
     * 可以使用selectMapsPage返回Map集合列表
     */
    @Test
    public void testSelectMapsPage() {

        Page<User> page = new Page<>(2, 5);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age");//可以查询任意字段属性

        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, queryWrapper);

        //注意：此行必须使用 mapIPage 获取记录列表，否则会有数据类型转换错误
        List<Map<String, Object>> records = mapIPage.getRecords();
        records.forEach(System.out::println);

        System.out.println(mapIPage.getCurrent());
        System.out.println(mapIPage.getPages());
        System.out.println(mapIPage.getSize());
        System.out.println(mapIPage.getTotal());
        //        System.out.println(mapIPage.hasNext());
        //        System.out.println(mapIPage.hasPrevious());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

}
