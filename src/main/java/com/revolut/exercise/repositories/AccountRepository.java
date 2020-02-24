package com.revolut.exercise.repositories;

import com.revolut.exercise.api.BaseRepository;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccountRepository implements BaseRepository<Account> {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private static final AccountRepository INSTANCE = new AccountRepository();

    final Map<Long, Account> store = new ConcurrentHashMap<>();
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private long idCounter = 0;

    public static AccountRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Account find(long id) throws EntityNotFoundException {
        lock.readLock().lock();
        try {
            if (store.containsKey(id)) {
                return store.get(id);
            }
            LOGGER.error("Account not found for given id : {}", id);
            throw new EntityNotFoundException("Account not found for given id :" + id);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Account createOrUpdate(final Account item) throws EntityNotFoundException {
        Assert.requireNonNull(item, "Account");
        Assert.requireNonNegative(item.getBalance().getAmount(), "Account balance");
        try {
            lock.writeLock().lock();
            if (null == item.getId()) {
                item.setId(idCounter++);
            } else {
                if (!store.containsKey(item.getId())) {
                    LOGGER.error("Account not found for given id : {}", item.getId());
                    throw new EntityNotFoundException("Account not found for given id :" + item.getId());
                }
            }
            store.put(item.getId(), item);
            LOGGER.info("Account created/updated successfully with id: {}", item.getId());
            return store.get(item.getId());
        } finally {
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
            LOGGER.error("Account not found for given id : {}", id);
            throw new EntityNotFoundException("Account not found for given id :" + id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void deleteAll() {
        lock.writeLock().lock();
        try {
            store.clear();
            idCounter = 0;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
