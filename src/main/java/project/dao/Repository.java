package project.dao;

import java.util.Collection;

public interface Repository<K,V> {
    Collection<V> findAll();

    V findById(K id);

    V create(V entity);

    V update(V entity);

    V deleteById(K id);
}
