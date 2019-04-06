/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.service;

import com.payswitch.model.Privilege;
import com.payswitch.repository.PrivilegeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class PrivilegeService {
    
        
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Privilege findByName(String name) {
        return privilegeRepository.findByName(name);
    }

    public void delete(Privilege entity) {
        privilegeRepository.delete(entity);
    }

    public List<Privilege> findAll() {
        return privilegeRepository.findAll();
    }

    public List<Privilege> findAll(Sort sort) {
        return privilegeRepository.findAll(sort);
    }

    public List<Privilege> findAllById(Iterable<Long> ids) {
        return privilegeRepository.findAllById(ids);
    }


    public Privilege getOne(Long id) {
        return privilegeRepository.getOne(id);
    }

    public <S extends Privilege> S save(S entity) {
        return privilegeRepository.save(entity);
    }
    
}
