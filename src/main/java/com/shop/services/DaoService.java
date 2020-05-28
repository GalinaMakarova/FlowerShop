package com.shop.services;

import java.util.List;
import java.util.Optional;

public interface DaoService<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T add(T t);

    boolean update(T t);

    void deleteById(Long id);

    void delete(T t);
}
