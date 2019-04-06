/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.controller;

import com.payswitch.config.aspect.Loggable;
import com.payswitch.model.Formatter;
import com.payswitch.model.FormatterMessageType;
import com.payswitch.service.FormatterMessageTypeService;
import com.payswitch.service.FormatterService;
import com.payswitch.storage.StorageService;
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
public class FormatterMessageTypeController {

    @Autowired
    private FormatterMessageTypeService formatterMITService;

    @GetMapping("/formatter-mit")
    @PreAuthorize("hasAuthority('READ_PARTICIPANT')")
    @Loggable
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("formatter/formatter-mit");
        mv.addObject("formatter_mits", formatterMITService.findAll());
        return mv;
    }

    @GetMapping("/formatter-mit/register")
    @PreAuthorize("hasAuthority('CREATE_PARTICIPANT')")
    @Loggable
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("formatter/formatter-mit");
        mv.addObject("isRegister", true);
        return mv;
    }

    @PostMapping(value = "/formatter-mit/save")
    @PreAuthorize("hasAuthority('CREATE_PARTICIPANT')")
    @Loggable
    public String save(
            //            @RequestParam("photo") MultipartFile photo,
            @RequestParam("type") Integer type,
            RedirectAttributes redirectAttributes
    ) {

        FormatterMessageType formatter = new FormatterMessageType();
        formatter.setMessageType(type);
        formatterMITService.save(formatter);

        redirectAttributes.addFlashAttribute("message",
                "You successfully registered");
        return "redirect:/formatter-mit/" + formatter.getId();
    }

    @GetMapping("/formatter-mit/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PARTICIPANT')")
    @Loggable
    public ModelAndView update(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("formatter-mit/form");
        mv.addObject("isUpdate", true);
        mv.addObject("formatter-mit", formatter - mitService.getOne(id));
        return mv;
    }

    @GetMapping("/formatter-mit/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_PARTICIPANT')")
    @Loggable
    public String delete(@PathVariable("id") Long id) {
        formatter - mitService.delete(formatter - mitService.getOne(id));
        return "redirect:/formatter-mit";
    }

    @GetMapping("/formatter-mit/detail/{id}")
    @PreAuthorize("hasAuthority('READ_PARTICIPANT')")
    @Loggable
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("formatter-mit/detail");
        mv.addObject("formatter-mit", formatter - mitService.getOne(id));
        return mv;
    }

}
