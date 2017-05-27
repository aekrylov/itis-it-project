package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/26/17 12:21 AM
 */
public class RepositoryRegister {
    private static final Map<Class, Repository<?, ? extends Serializable>> repositories = new HashMap<>();

    public static <T> void register(Class<T> entityClass, Repository<T, ? extends Serializable> repository) {
        repositories.put(entityClass, repository);
    }

    @SuppressWarnings("unchecked")
    public static <T> Repository<T, Serializable> get(Class<T> entityClass) {
        return (Repository<T, Serializable>) repositories.get(entityClass);
    }
}
