/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.controller;

import com.console.model.Participant;
import com.console.service.ParticipantService;
import com.console.storage.StorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Dell
 */
@Controller
public class ParticipantController {
    
    private final StorageService storageService;
    
    @Autowired
    public ParticipantController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @Autowired
    private ParticipantService participantService;
    
    @GetMapping("/participant")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("participant/home");
        mv.addObject("participants", participantService.findAll());
        return mv;
    }
    
    @GetMapping("/participant/register")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("participant/form");
        mv.addObject("isRegister", true);
        return mv;
    }
    
    @GetMapping("/participant/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("participant/form");
        mv.addObject("isUpdate", true);
        mv.addObject("participant", participantService.getOne(id));
        return mv;
    }
    
    @GetMapping("/participant/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        participantService.delete(participantService.getOne(id));
        return "redirect:/participant";
    }
    
    @PostMapping("/participant/save")
    public ModelAndView save(
            @RequestBody("photo") MultipartFile photo,
            @RequestBody("firstName") String firstName,
            @RequestBody("lastName") String lastName,
            @RequestBody("status") String status,
            @RequestBody("gender") boolean gender,
            @RequestBody("tazkra_serial_number") String tazkraSerialNumber,
            @RequestBody("tazkra_page_number") int tazkraPageNumber,
            @RequestBody("tazkra_juld") String tazkraJuld,
            @RequestBody("phone") String phone,
            @RequestBody("email") String email,
            @RequestBody("address") String address,
            @RequestBody("province") String province,
            @RequestBody("district") String district,
            @RequestBody("birth_place") String birthPlace,
            @RequestBody("birth_year") String birthYear,
            @RequestBody("education") String education,
            RedirectAttributes redirectAttributes
    ) {
        
        if (photo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new ModelAndView("participant/register");
        }
        
        String fileName = photo.getOriginalFilename();
        storageService.store(photo);
        
        Participant part = new Participant();
        part.setFirstName(firstName);
        part.setLastName(lastName);
        part.setPhone(phone);
        part.setEmail(email);
        part.setAddress(address);
        part.setBirthPlace(birthPlace);
        part.setBirthYear(birthYear);
        part.setProvince(province);
        part.setDistrict(district);
        part.setEductaion(education);
        part.setGender(gender);
        part.setTazkraSerialNumber(tazkraSerialNumber);
        part.setTazkraPageNumber(tazkraPageNumber);
        part.setTazkraJuld(tazkraJuld);
        part.setStatus(status);
        part.setEntered(false);
        part.setPhoto(fileName);
        
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + photo.getOriginalFilename() + "'");
        
        ModelAndView mv = new ModelAndView("participant/detail");
        mv.addObject("participant", part);
        
        return mv;
    }
}
