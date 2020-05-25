package com.shop.services;

import java.util.List;

public interface DaoService<T> {
    List<T> findAll();

    T findById(Long id);

    void add(T t) throws Exception;

    void update(T t);

    void delete(Long id);
}
