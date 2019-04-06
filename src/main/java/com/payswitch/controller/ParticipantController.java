/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.controller;

import com.payswitch.config.aspect.Loggable;
import com.payswitch.model.Participant;
import com.payswitch.service.ParticipantService;
import com.payswitch.storage.StorageService;
import com.payswitch.util.QrCodeUtil;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PreAuthorize("hasAuthority('READ_PARTICIPANT')")
    @Loggable
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("participant/home");
        mv.addObject("participants", participantService.findAll());
        return mv;
    }

    @GetMapping("/participant/register")
    @PreAuthorize("hasAuthority('CREATE_PARTICIPANT')")
    @Loggable
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("participant/form");
        mv.addObject("isRegister", true);
        return mv;
    }

    @PostMapping(value = "/participant/save")
    @PreAuthorize("hasAuthority('CREATE_PARTICIPANT')")
    @Loggable
    public String saveParticipant(
            //            @RequestParam("photo") MultipartFile photo,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("status") String status,
            @RequestParam("gender") boolean gender,
            @RequestParam("tazkra_serial_number") String tazkraSerialNumber,
            @RequestParam("tazkra_page_number") int tazkraPageNumber,
            @RequestParam("tazkra_juld") String tazkraJuld,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("province") String province,
            @RequestParam("district") String district,
            @RequestParam("birth_place") String birthPlace,
            @RequestParam("birth_year") String birthYear,
            @RequestParam("education") String education,
            RedirectAttributes redirectAttributes
    ) {

//        if (photo.isEmpty()) {
//            System.out.println("IMAGE IS EMPTY ");
//
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "participant/register";
//        }
//        String fileName = photo.getOriginalFilename();
//        storageService.store(photo);
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
        part.setPhoto("test_url");
        participantService.save(part);

        redirectAttributes.addFlashAttribute("message",
                "You successfully registered");
//        ModelAndView mv = new ModelAndView("participant/detail");
//        mv.addObject("participant", part);
//        mv.addObject("qrimg", generateQrCode(part));
        return "redirect:/participant/detail/"+part.getId();
    }

    @GetMapping("/participant/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PARTICIPANT')")
    @Loggable
    public ModelAndView update(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("participant/form");
        mv.addObject("isUpdate", true);
        mv.addObject("participant", participantService.getOne(id));
        return mv;
    }

    @GetMapping("/participant/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_PARTICIPANT')")
    @Loggable
    public String delete(@PathVariable("id") Long id) {
        participantService.delete(participantService.getOne(id));
        return "redirect:/participant";
    }

    @GetMapping("/participant/detail/{id}")
    @PreAuthorize("hasAuthority('READ_PARTICIPANT')")
    @Loggable
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("participant/detail");
        mv.addObject("participant", participantService.getOne(id));
        mv.addObject("qrimg", QrCodeUtil.generateQrCode(participantService.getOne(id)));
        return mv;
    }

    @GetMapping("/participant/card/{id}")
    @PreAuthorize("hasAuthority('PRINT_PARTICIPANT_CARD')")
    @Loggable
    public ModelAndView card(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("participant/card");
        mv.addObject("participant", participantService.getOne(id));
        mv.addObject("qrimg", QrCodeUtil.generateQrCode(participantService.getOne(id)));
        return mv;
    }
}
