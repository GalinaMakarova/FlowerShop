package com.shop.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DaoCRUD<T> {
    @Transactional
    List<T> findAll();

    @Transactional
    T findById(Long id);

    @Transactional
    void add(T t);

    @Transactional
    void update(T t);

    @Transactional
    void delete(Long id);
}
