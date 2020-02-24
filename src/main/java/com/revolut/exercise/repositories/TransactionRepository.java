package com.revolut.exercise.repositories;

import com.revolut.exercise.api.BaseRepository;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.TransactionDetails;
import com.revolut.exercise.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionRepository implements BaseRepository<TransactionDetails> {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);

    private static final TransactionRepository INSTANCE = new TransactionRepository();

    final Map<Long, TransactionDetails> store = new ConcurrentHashMap<>();
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static long idCounter = 0;

    public static TransactionRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<TransactionDetails> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public TransactionDetails find(long id) throws EntityNotFoundException {
        lock.readLock().lock();
        try {
            if (store.containsKey(id)) {
                return store.get(id);
            }
            LOGGER.error("TransactionDetails not found for given id : {}", id);
            throw new EntityNotFoundException("TransactionDetails not found for given id :"+id);
        }finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public TransactionDetails createOrUpdate(final TransactionDetails item) throws EntityNotFoundException{
        Assert.requireNonNull(item,"TransactionDetails");
        Assert.requireNonNull(item.getAccountId(), "TransactionDetails account details");
        try {
            lock.writeLock().lock();
            if(null == item.getId()){
                item.setId(idCounter++);
            }else{
                if(!store.containsKey(item.getId())){
                    LOGGER.error("TransactionDetails not found for given id : {}", item.getId());
                    throw new EntityNotFoundException("TransactionDetails not found for given id :"+item.getId());
                }
            }
            store.put(item.getId(), item);
            LOGGER.info("TransactionDetails created/updated successfully with id: {}", item.getId());
            return store.get(item.getId());
        }finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {
        lock.writeLock().lock();
        try {
            if (store.containsKey(id)) {
                store.remove(id);
                return;
            }
            LOGGER.error("TransactionDetails not found for given id : {}", id);
            throw new EntityNotFoundException("TransactionDetails not found for given id :"+id);
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
