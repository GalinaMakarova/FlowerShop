package dao;

import java.util.Set;

public interface DaoCRUD<T> {
    public Set<T> findAll();
    public T findById(Long id);
    public void add(T t);
    public void update(T t);
    public void delete(T t);
}
