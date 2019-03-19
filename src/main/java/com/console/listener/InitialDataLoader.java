/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.listener;

import com.console.model.Privilege;
import com.console.model.Role;
import com.console.model.User;
import com.console.repository.PrivilegeRepository;
import com.console.repository.RoleRepository;
import com.console.repository.UserRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dell
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent e) {

        if (alreadySetup) {
            return;
        }

        if (userRepository.findAll().size() >= 1) {
            return;
        }

        Privilege privilegeReadPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege userReadPrivilege = createPrivilegeIfNotFound("READ_USER");
        Privilege userCreatePrivilege = createPrivilegeIfNotFound("CREATE_USER");
        Privilege userDeletePrivilege = createPrivilegeIfNotFound("DELETE_USER");
        Privilege userUpdatePrivilege = createPrivilegeIfNotFound("UPDATE_USER");
        Privilege roleReadPrivilege = createPrivilegeIfNotFound("READ_ROlE");
        Privilege roleCreatePrivilege = createPrivilegeIfNotFound("CREATE_ROLE");
        Privilege roleDeletePrivilege = createPrivilegeIfNotFound("DELETE_ROLE");
        Privilege roleUpdatePrivilege = createPrivilegeIfNotFound("UPDATE_ROLE");
        Privilege participantReadPrivilege = createPrivilegeIfNotFound("READ_PARTICIPANT");
        Privilege participantCreatePrivilege = createPrivilegeIfNotFound("CREATE_PARTICIPANT");
        Privilege participantDeletePrivilege = createPrivilegeIfNotFound("DELETE_PARTICIPANT");
        Privilege participantUpdatePrivilege = createPrivilegeIfNotFound("UPDATE_PARTICIPANT");
        Privilege printParticipantCardPrivilege = createPrivilegeIfNotFound("PRINT_PARTICIPANT_CARD");

        List<Privilege> adminPrivileges
                = Arrays.asList(
                        privilegeReadPrivilege,
                        userReadPrivilege,
                        userCreatePrivilege,
                        userDeletePrivilege,
                        userUpdatePrivilege,
                        userUpdatePrivilege,
                        roleReadPrivilege,
                        roleCreatePrivilege,
                        roleDeletePrivilege,
                        roleUpdatePrivilege,
                        participantReadPrivilege,
                        participantCreatePrivilege,
                        participantUpdatePrivilege,
                        participantDeletePrivilege,
                        printParticipantCardPrivilege
                );

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(
                participantReadPrivilege,
                participantCreatePrivilege,
                participantUpdatePrivilege,
                participantDeletePrivilege));

//        SETUP USER
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName("Mohammad Badar");
        user.setLastName("Hashimi");
        user.setPassword(passwordEncoder.encode("admin1235"));
        user.setEmail("admin@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
