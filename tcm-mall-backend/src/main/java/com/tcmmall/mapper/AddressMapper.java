package com.tcmmall.mapper;

import com.tcmmall.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    int insert(Address address);
    int update(Address address);
    int deleteById(Long id);
    int updateDefaultByUserId(@Param("userId") Long userId, @Param("isDefault") Integer isDefault);
    Address selectById(Long id);
    List<Address> selectByUserId(Long userId);
    Address selectDefaultByUserId(Long userId);
}
