package com.tcmmall.mapper;

import com.tcmmall.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectList(@Param("keyword") String keyword);
}
