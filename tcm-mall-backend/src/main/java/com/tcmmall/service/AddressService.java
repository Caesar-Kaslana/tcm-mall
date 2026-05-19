package com.tcmmall.service;

import com.tcmmall.entity.Address;

import java.util.List;

public interface AddressService {
    int add(Address address);
    int update(Address address);
    int delete(Long id);
    Address getById(Long id);
    List<Address> list(Long userId);
    int setDefault(Long id, Long userId);
}
