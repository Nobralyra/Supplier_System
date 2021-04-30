package finalproject.suppliersystem.core;

import java.util.List;

public interface IService<E> {
    void save(E element);
    E findById(Long id);
    List<E> findAll();
    void deleteByID(Long id);
}
