package project.service;

import java.util.Collection;

public interface Service<E> {
    Collection<E> getAll();

    E getById(Integer id);

    E add(E entity) ;

    E update(E entity);

    E delete(Integer id);
}
