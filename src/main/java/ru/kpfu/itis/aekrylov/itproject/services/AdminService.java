package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/27/17 2:39 PM
 */

@Service
public class AdminService {

    @PersistenceContext
    private EntityManager em;

    private final EntityManagerFactory emf;
    private final Map<String, EntityType<?>> entityTypeMap = new HashMap<>();

    @Autowired
    public AdminService(EntityManagerFactory emf) {
        this.emf = emf;

        Set<EntityType<?>> entityTypes = emf.getMetamodel().getEntities();
        entityTypes.forEach(type -> entityTypeMap.put(type.getName(), type));
    }

    public Set<String> getEntities() {
        return entityTypeMap.keySet();
    }

    public Set<String> getColumnNames(String entityName) {
        EntityType<?> type = entityTypeMap.get(entityName);
        return type.getSingularAttributes().stream()
                .map(Attribute::getName)
                .collect(Collectors.toSet());
    }

    public Page query(String entityName, String str, Pageable pageable) {
        EntityType<?> type = entityTypeMap.get(entityName);
        TypedQuery<?> tq = (str != null) ? query(type, str.toLowerCase()) : query(type);

        int total = tq.getResultList().size();

        tq.setFirstResult(pageable.getOffset());
        tq.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(tq.getResultList(), pageable, total);
    }

    private <T> TypedQuery<T> query(EntityType<T> entityType, final String strLike) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(entityType.getJavaType());

        Root<T> root = q.from(entityType);

        List<Predicate> likes = entityType.getSingularAttributes().stream()
                .map(attr -> root.get(attr).as(String.class))
                .map(path -> cb.like(path, "%"+strLike+"%"))
                .collect(Collectors.toList());

        q.where(cb.or(likes.toArray(new Predicate[0])));
        return em.createQuery(q);
    }

    private <T> TypedQuery<T> query(EntityType<T> entityType) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(entityType.getJavaType());

        q.from(entityType);
        return em.createQuery(q);
    }


}
