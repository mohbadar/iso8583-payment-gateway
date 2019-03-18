/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.controller;

import com.console.config.aspect.Loggable;
import com.console.model.Role;
import com.console.model.User;
import com.console.repository.UserRepository;
import com.console.service.RoleService;
import com.console.service.UserService;
import com.console.util.Utility;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dell
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Loggable
    @GetMapping("/user")
    public ModelAndView userIndex() {
        ModelAndView mv = new ModelAndView("user/home");
        mv.addObject("users", userService.findAll());
        return mv;
    }

    @Loggable
    @GetMapping("/user/register")
    public ModelAndView userRegister() {
        ModelAndView mv = new ModelAndView("user/form");
        mv.addObject("roles", roleService.findAll());
        mv.addObject("isRegister", true);
        return mv;
    }

    @Loggable
    @PostMapping("/user/register")
    public String register(
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("password") String password,
            @RequestParam("roles") long[] ids
    ) {

        Iterable<Long> rolesId = Utility.toIterable(ids);
        List<Role> roles = roleService.findAllById(rolesId);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        user.setRoles(roles);

        userService.save(user);

        return "redirect:/user";
    }

    @Loggable
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(userService.getOne(id));
        return "redirect:/user";
    }

    @Loggable
    @GetMapping("/user/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("user/form");
        mv.addObject("user", userService.getOne(id));
        mv.addObject("isUpdate", true);
        mv.addObject("roles", roleService.findAll());
        return mv;
    }

    @Loggable
    @PostMapping("/user/update")
    public String update(
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("password") String password,
            @RequestParam("roles") long[] ids,
            @RequestParam("id") long id
    ) {

        Iterable<Long> rolesId = Utility.toIterable(ids);
        List<Role> roles = roleService.findAllById(rolesId);

        User user = userService.getOne(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        user.setRoles(roles);

        userService.save(user);

        return "redirect:/user";
    }
}
