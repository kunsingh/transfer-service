package com.revolut.exercise.api;


import com.revolut.exercise.exceptions.EntityNotFoundException;

import java.util.List;

public interface BaseRepository<E extends DataBase> {

    List<E> findAll();

    E find(long id) throws EntityNotFoundException;

    E createOrUpdate(E item) throws EntityNotFoundException;

    void delete(long id) throws EntityNotFoundException;

    void deleteAll() ;

}
