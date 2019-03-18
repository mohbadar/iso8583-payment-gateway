/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.controller;

import com.console.config.aspect.Loggable;
import com.console.model.Participant;
import com.console.service.ParticipantService;
import com.console.storage.StorageService;
import com.console.util.QrCodeUtil;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String QR_CHARACTER_SET = "UTF-8"; // or "ISO-8859-1"
    private static final String QR_ERROR_CORRECTION_LEVEL = "L";
    private static final int WIDTH = 350;
    private static final int HEIGHT = 350;
    private static final String QR_IMAGE_TYPE = "png";

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

    @Loggable
    @PostMapping(value = "/participant/save")
    public ModelAndView saveParticipant(
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

        System.out.println("BEFORE IMAGE " + firstName + ":" + lastName);

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
        ModelAndView mv = new ModelAndView("participant/detail");
        mv.addObject("participant", new Participant());
        mv.addObject("qrimg", generateQrCode(part));
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

    @GetMapping("/participant/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("participant/detail");
        mv.addObject("participant", participantService.getOne(id));
        mv.addObject("qrimg", generateQrCode(participantService.getOne(id)));
        return mv;
    }

    public String generateQrCode(Participant p) {

        String content = "";
        String qrimg = "";

        content += "1:SECURE*";
        content += "2:CONCOUL-QR-01*";
        content += "3:UTF-8*";
        content += "4:S*";

        content += "5:" + p.getId() + "*";
        content += "6:" + p.getFirstName() + "*";
        content += "7:" + p.getLastName() + "*";
        content += "8:" + p.getProvince() + "*";
        content += "9:" + p.getTazkraSerialNumber() + "*";
        content += "10:" + p.getGender() + "*";

        try {
            qrimg = new QrCodeUtil().generateQRCImg(QR_CHARACTER_SET, QR_ERROR_CORRECTION_LEVEL, WIDTH, HEIGHT, content, QR_IMAGE_TYPE);
        } catch (WriterException ex) {
        } catch (IOException ex) {
        }

        return qrimg;
    }

    @GetMapping("/participant/card/{id}")
    public ModelAndView card(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("participant/card");
        mv.addObject("participant", participantService.getOne(id));
        mv.addObject("qrimg", generateQrCode(participantService.getOne(id)));
        return mv;
    }
}
