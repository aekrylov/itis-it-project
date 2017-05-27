package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/26/17 12:23 AM
 */
@NoRepositoryBean
class RegisteringJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

    public RegisteringJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        RepositoryRegister.register(entityInformation.getJavaType(), this);
    }
}
