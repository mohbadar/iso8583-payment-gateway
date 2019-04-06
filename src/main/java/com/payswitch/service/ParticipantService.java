/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.service;

import com.payswitch.model.Participant;
import com.payswitch.repository.ParticipantRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void delete(Participant entity) {
        participantRepository.delete(entity);
    }

    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    public List<Participant> findAll(Sort sort) {
        return participantRepository.findAll(sort);
    }

    public List<Participant> findAllById(Iterable<Long> ids) {
        return participantRepository.findAllById(ids);
    }

    public Participant getOne(Long id) {
        return participantRepository.getOne(id);
    }

    public <S extends Participant> S save(S entity) {
        return participantRepository.save(entity);
    }

}
