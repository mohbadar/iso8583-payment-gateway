/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.controller;

import com.payswitch.config.aspect.Loggable;
import com.payswitch.model.Formatter;
import com.payswitch.model.Participant;
import com.payswitch.service.FormatterService;
import com.payswitch.service.ParticipantService;
import com.payswitch.storage.StorageService;
import com.payswitch.util.QrCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Dell
 */
@Controller
public class FomatterController {

    private final StorageService storageService;

    @Autowired
    public FomatterController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private FormatterService formatterService;

    @GetMapping("/formatter")
    @PreAuthorize("hasAuthority('READ_PARTICIPANT')")
    @Loggable
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("formatter/formatter");
        mv.addObject("formatters", formatterService.findAll());
        return mv;
    }

    @GetMapping("/formatter/register")
    @PreAuthorize("hasAuthority('CREATE_PARTICIPANT')")
    @Loggable
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("formatter/formatter");
        mv.addObject("isRegister", true);
        return mv;
    }

    @PostMapping(value = "/formatter/save")
    @PreAuthorize("hasAuthority('CREATE_PARTICIPANT')")
    @Loggable
    public String save(
            //            @RequestParam("photo") MultipartFile photo,
            @RequestParam("name") String name,
            @RequestParam("desc") String desc,
            RedirectAttributes redirectAttributes
    ) {

        Formatter formatter = new Formatter();
        formatter.setName(name);
        formatter.setDesc(desc);
        formatterService.save(formatter);

        redirectAttributes.addFlashAttribute("message",
                "You successfully registered");
        return "redirect:/formatter/" + formatter.getId();
    }

    @GetMapping("/formatter/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PARTICIPANT')")
    @Loggable
    public ModelAndView update(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("formatter/form");
        mv.addObject("isUpdate", true);
        mv.addObject("formatter", formatterService.getOne(id));
        return mv;
    }

    @GetMapping("/formatter/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_PARTICIPANT')")
    @Loggable
    public String delete(@PathVariable("id") Long id) {
        formatterService.delete(formatterService.getOne(id));
        return "redirect:/formatter";
    }

    @GetMapping("/formatter/detail/{id}")
    @PreAuthorize("hasAuthority('READ_PARTICIPANT')")
    @Loggable
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("formatter/detail");
        mv.addObject("formatter", formatterService.getOne(id));
        return mv;
    }

}
