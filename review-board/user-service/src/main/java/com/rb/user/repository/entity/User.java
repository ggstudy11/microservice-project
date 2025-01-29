package com.rb.user.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 1/29/2025 5:14 PM
 * @description：用户实体类
 * @modified By：
 *
*/
@Data
@TableName(value = "user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String phone;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
