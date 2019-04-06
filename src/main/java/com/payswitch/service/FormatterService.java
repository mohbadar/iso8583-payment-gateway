/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.service;

import com.payswitch.model.Formatter;
import com.payswitch.repository.FormatterRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class FormatterService {

    @Autowired
    private FormatterRepository formatterRepository;

    public void delete(Formatter entity) {
        formatterRepository.delete(entity);
    }

    public List<Formatter> findAll() {
        return formatterRepository.findAll();
    }

    public List<Formatter> findAll(Sort sort) {
        return formatterRepository.findAll(sort);
    }

    public List<Formatter> findAllById(Iterable<Long> ids) {
        return formatterRepository.findAllById(ids);
    }

    public Formatter getOne(Long id) {
        return formatterRepository.getOne(id);
    }

    public <S extends Formatter> S save(S entity) {
        return formatterRepository.save(entity);
    }

}
