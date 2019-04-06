/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.service;

import com.payswitch.model.User;
import com.payswitch.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Long count() {
        return repo.count();
    }

    public User getOne(Long id) {
        return repo.getOne(id);
    }

    public List<User> findAllById(Iterable<Long> ids) {
        return repo.findAllById(ids);
    }

    public void delete(User user) {
        repo.delete(user);
    }

}
