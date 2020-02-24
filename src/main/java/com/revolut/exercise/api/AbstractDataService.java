package com.revolut.exercise.api;

import com.revolut.exercise.exceptions.EntityNotFoundException;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Common CRUD implementations
 * @param <T> {@link DataBase}
 * @param <R> {@link BaseRepository}
 */
public abstract class AbstractDataService<T extends DataBase, R extends BaseRepository<T>> implements DataService<T> {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(AbstractDataService.class);

    private final R repository;


    public AbstractDataService(final R repository) {
        this.repository = repository;
    }

    @Override
    public T find(final long id) throws EntityNotFoundException {
        return findEntityChecked(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T createOrUpdate(T item) throws EntityNotFoundException {
            return repository.createOrUpdate(item);
    }

    @Override
    public void delete(long id) throws EntityNotFoundException{
        findEntityChecked(id);
        repository.delete(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    public R getRepository() {
        return repository;
    }

    protected T findEntityChecked(Long id) throws EntityNotFoundException {
        T e = repository.find(id);
        if(e == null){
            throw new EntityNotFoundException("Could not find entity with id: " + id);
        }
        return e;
    }
}
