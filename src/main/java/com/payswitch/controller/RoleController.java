/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.controller;

import com.payswitch.config.aspect.Loggable;
import com.payswitch.model.Privilege;
import com.payswitch.model.Role;
import com.payswitch.repository.PrivilegeRepository;
import com.payswitch.service.PrivilegeService;
import com.payswitch.service.RoleService;
import com.payswitch.util.Utility;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dell
 */
@Controller
public class RoleController {

    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private RoleService roleService;

    @Loggable
    @GetMapping("/role")
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("role/home");
        mv.addObject("roles", roleService.findAll());
        return mv;
    }

    @Loggable
    @GetMapping("/role/register")
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("role/form");
        mv.addObject("privileges", privilegeService.findAll());
        mv.addObject("isRegister", true);
        return mv;
    }

    @Loggable
    @PostMapping("/role/save")
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public String save(@RequestParam("name") String name, @RequestParam("privileges") long[] ids) {
        Iterable<Long> privs = Utility.toIterable(ids);
        List<Privilege> privileges = privilegeService.findAllById(privs);

        Role role = new Role();
        role.setName(name);
        role.setPrivileges(privileges);
        roleService.save(role);

        return "redirect:/role";
    }

    @Loggable
    @GetMapping("/role/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(roleService.getOne(id));
        return "redirect:/role";
    }

    @Loggable
    @GetMapping("/role/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ModelAndView update(@PathVariable Long id) {
        Role role = roleService.getOne(id);
        boolean isUpdate = true;
        ModelAndView mv = new ModelAndView("role/form");
        mv.addObject("role", role);
        mv.addObject("isUpdate", isUpdate);
        mv.addObject("privileges", privilegeService.findAll());
        return mv;
    }

    @Loggable
    @PostMapping("/role/update")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public String update(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("privileges") long[] ids) {
        Iterable<Long> privs = Utility.toIterable(ids);
        List<Privilege> privileges = privilegeService.findAllById(privs);

        Role role = roleService.getOne(id);
        role.setName(name);
        role.setPrivileges(privileges);
        roleService.save(role);

        return "redirect:/role";
    }

}
