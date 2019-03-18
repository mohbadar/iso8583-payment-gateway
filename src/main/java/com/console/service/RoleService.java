/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.service;

import com.console.model.Role;
import com.console.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public void delete(Role role) {
        roleRepository.delete(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findAll(Sort sort) {
        return roleRepository.findAll(sort);
    }

    public List<Role> findAllById(Iterable<Long> ids) {
        return roleRepository.findAllById(ids);
    }


    public Role getOne(Long id) {
        return roleRepository.getOne(id);
    }

    public <S extends Role> S save(S entity) {
        return roleRepository.save(entity);
    }

}
