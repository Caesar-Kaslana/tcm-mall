package com.tcmmall.service.impl;

import com.tcmmall.entity.Address;
import com.tcmmall.mapper.AddressMapper;
import com.tcmmall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    @Transactional
    public int add(Address address) {
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            addressMapper.updateDefaultByUserId(address.getUserId(), 0);
        }
        return addressMapper.insert(address);
    }

    @Override
    @Transactional
    public int update(Address address) {
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            addressMapper.updateDefaultByUserId(address.getUserId(), 0);
        }
        return addressMapper.update(address);
    }

    @Override
    public int delete(Long id) {
        return addressMapper.deleteById(id);
    }

    @Override
    public Address getById(Long id) {
        return addressMapper.selectById(id);
    }

    @Override
    public List<Address> list(Long userId) {
        return addressMapper.selectByUserId(userId);
    }

    @Override
    @Transactional
    public int setDefault(Long id, Long userId) {
        addressMapper.updateDefaultByUserId(userId, 0);
        Address update = new Address();
        update.setId(id);
        update.setIsDefault(1);
        return addressMapper.update(update);
    }
}
