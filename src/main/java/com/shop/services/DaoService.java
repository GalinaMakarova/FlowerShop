package com.shop.services;

import java.util.Set;

public interface DaoService<T>{
    Set<T> findAll();
    T findById(Long id);
    void add(T t);
    void update(T t);
    void delete(T t);
}
