package com.tcmmall.mapper;

import com.tcmmall.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    int insert(Notice notice);
    int update(Notice notice);
    int deleteById(Long id);
    Notice selectById(Long id);
    List<Notice> selectList(@Param("status") Integer status);
}
