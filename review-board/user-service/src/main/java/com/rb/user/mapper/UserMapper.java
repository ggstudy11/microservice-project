package com.rb.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rb.user.repository.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 1/29/2025 5:21 PM
 * @description：用户表mapper
 * @modified By：
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
