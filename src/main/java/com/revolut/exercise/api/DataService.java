package com.revolut.exercise.api;
import com.revolut.exercise.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Common CRUD methods
 * @param <T>
 */
public interface DataService<T extends DataBase> {

    T find(long id) throws EntityNotFoundException;

    List<T> findAll();

    T createOrUpdate(T item) throws EntityNotFoundException;

    void delete(long id) throws EntityNotFoundException;

    void deleteAll();
}
