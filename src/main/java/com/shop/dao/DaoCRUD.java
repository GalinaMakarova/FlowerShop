package com.shop.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface DaoCRUD<T> {
    @Transactional
    Set<T> findAll();

    @Transactional
    T findById(Long id);

    @Transactional
    void add(T t);

    @Transactional
    void update(T t);

    @Transactional
    void delete(T t);
}
