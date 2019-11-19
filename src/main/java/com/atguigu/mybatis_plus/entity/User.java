package com.atguigu.mybatis_plus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author eternity
 * @create 2019-11-19 13:56
 */
@Data
public class User {

//    @TableId(type = IdType.NONE)//该类型为未设置主键类型
    /**
     * 用户输入ID
     * 该类型可以通过自己注册自动填充插件进行填充
     */
//    @TableId(type = IdType.INPUT)
//    @TableId(type = IdType.ID_WORKER)//全局唯一ID（(idWorker)分布式ID生成器）
//    @TableId(type = IdType.UUID)//全局唯一ID (UUID)
//    @TableId(type = IdType.ID_WORKER_STR)//字符串全局唯一ID (idWorker 的字符串表示)
    @TableId(type = IdType.AUTO)//数据库ID自增
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Integer viewCount;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //@TableField(fill = FieldFill.UPDATE)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    private Integer version;


    @TableLogic
    private Integer deleted;

}
