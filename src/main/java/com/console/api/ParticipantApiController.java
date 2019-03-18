/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.api;

import com.console.model.Participant;
import com.console.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dell
 */
@RestController
@RequestMapping(value = "/api")
public class ParticipantApiController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping("/participant-by-id")
    public Participant getParanticipantById(@RequestParam("id") Long id) {
        if (id == 0 || id == null) {
            return null;
        }
        return participantService.getOne(id);
    }

}
