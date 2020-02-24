package com.revolut.exercise.repositories;

import com.revolut.exercise.api.BaseRepository;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.TransferDetails;
import com.revolut.exercise.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransferRepository implements BaseRepository<TransferDetails> {

    private final Logger LOGGER = LoggerFactory.getLogger(TransferRepository.class);

    private static final TransferRepository INSTANCE = new TransferRepository();

    final Map<Long, TransferDetails> store = new ConcurrentHashMap<>();
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static long idCounter = 0;

    public static TransferRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<TransferDetails> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public TransferDetails find(long id) throws EntityNotFoundException {
        try {
            lock.readLock().lock();
            if (store.containsKey(id)) {
                return store.get(id);
            }
            LOGGER.error("Account not found for given id : {}", id);
            throw new EntityNotFoundException("TransferDetails not found for given id :" + id);
        }finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public TransferDetails createOrUpdate(final TransferDetails item) throws EntityNotFoundException{
        Assert.requireNonNull(item,"TransferDetails");
        try {
            lock.writeLock().lock();
            if(null == item.getId()){
                item.setId(idCounter++);
            }else{
                if(!store.containsKey(item.getId())){
                    LOGGER.error("Account not found for given id : {}", item.getId());
                    throw new EntityNotFoundException("TransferDetails not found for given id :"+item.getId());
                }
            }
            store.put(item.getId(), item);
            return store.get(item.getId());
        }finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {
        try {
            lock.writeLock().lock();
            if (store.containsKey(id)) {
                store.remove(id);
                return;
            }
            LOGGER.error("Account not found for given id : {}", id);
            throw new EntityNotFoundException("TransferDetails not found for given id :" + id);
        }finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void deleteAll() {
        lock.writeLock().lock();
        try {
            store.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
