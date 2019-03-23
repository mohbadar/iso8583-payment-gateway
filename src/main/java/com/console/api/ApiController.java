/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.api;

import com.console.config.ParamsConfig;
import com.console.model.Participant;
import com.console.model.ParticipantDto;
import com.console.service.ParticipantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dell
 */
@RestController
@RequestMapping(value = "/api/participant")
public class ApiController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping(value = "/get/{id}", produces = {ParamsConfig.API_DATA_FORMAT})
    public ParticipantDto getParanticipantById(@PathVariable("id") Long id) {
        if (id == 0 || id == null) {
            return null;
        }
        Participant p = participantService.getOne(id);
        ParticipantDto dto = new ParticipantDto();
        dto.setId(p.getId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setGender(p.getGender());
        dto.setStatus(p.getStatus());
        dto.setTazkraSerialNumber(p.getTazkraSerialNumber());
        dto.setTazkraPageNumber(p.getTazkraPageNumber());
        dto.setTazkraJuld(p.getTazkraJuld());
        dto.setPhone(p.getPhone());
        dto.setEmail(p.getEmail());
        dto.setAddress(p.getAddress());
        dto.setProvince(p.getProvince());
        dto.setDistrict(p.getDistrict());
        dto.setBirthPlace(p.getBirthPlace());
        dto.setBirthYear(p.getBirthYear());
        dto.setPhoto(p.getPhoto());
        dto.setEductaion(p.getEductaion());
        dto.setEntered(p.isEntered());
        dto.setAllowed(p.isAllowed());
        
        return dto;
    }
    
    @GetMapping("/get")
    public List<Participant> getParticipants()
    {
        return participantService.findAll();
    }

}
