/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.service;

import com.payswitch.repository.UserRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Dell
 */
//public class CustomUserDetailsService implements UserDetailsService {
//
////    @Autowired
////    private UserRepository repo;
////
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        return repo
////                .findByUsername(username)
////                .map(u -> new org.springframework.security.core.userdetails.User(
////                u.getUsername(),
////                u.getPassword(),
////                u.isActive(),
////                u.isActive(),
////                u.isActive(),
////                u.isActive(),
////                AuthorityUtils.createAuthorityList(
////                        u.getRoles()
////                                .stream()
////                                .map(r -> "ROLE_" + r.getName().toUpperCase())
////                                .collect(Collectors.toList())
////                                .toArray(new String[]{}))))
////                .orElseThrow(() -> new UsernameNotFoundException("No user with "
////                + "the name " + username + "was found in the database"));
////    }
//
//}
