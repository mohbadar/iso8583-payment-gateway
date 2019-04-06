/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.service;

import com.payswitch.model.FormatterMessageType;
import com.payswitch.repository.FormatterMessageTypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class FormatterMessageTypeService {

    @Autowired
    private FormatterMessageTypeRepository formatterRepository;

    public void delete(FormatterMessageType entity) {
        formatterRepository.delete(entity);
    }

    public List<FormatterMessageType> findAll() {
        return formatterRepository.findAll();
    }

    public List<FormatterMessageType> findAll(Sort sort) {
        return formatterRepository.findAll(sort);
    }

    public List<FormatterMessageType> findAllById(Iterable<Long> ids) {
        return formatterRepository.findAllById(ids);
    }

    public FormatterMessageType getOne(Long id) {
        return formatterRepository.getOne(id);
    }

    public <S extends FormatterMessageType> S save(S entity) {
        return formatterRepository.save(entity);
    }

}
