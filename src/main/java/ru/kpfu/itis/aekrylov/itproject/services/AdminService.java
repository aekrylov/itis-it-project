package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/27/17 2:39 PM
 */

@Service
@Transactional
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

    public Object findOne(String entityName, int id) {
        EntityType<?> type = entityTypeMap.get(entityName);
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<?> q = cb.createQuery(type.getJavaType());

        Root<?> root = q.from(type);
        q.where(cb.equal(root.get("id"), cb.literal(id)));

        TypedQuery<?> tq = em.createQuery(q);
        return tq.getSingleResult();
    }

    public void save(String entityName, int id, Map<String, Object> values) {
        EntityType<?> type = entityTypeMap.get(entityName);
        save(type, id, values);
    }

    @SuppressWarnings("unchecked")
    private <T> void save(EntityType<T> type, int id, Map<String, Object> values) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaUpdate<T> cu = cb.createCriteriaUpdate(type.getJavaType());
        Root<T> root = cu.from(type);

        type.getSingularAttributes()
                .forEach(attr -> {
                    //cast attribute to the correct type
                    Expression expr = cb.literal(values.get(attr.getName())).as(attr.getJavaType());
                    cu.set(attr, expr);
                });

        cu.where(cb.equal(root.get("id"), id));

        Query tq = em.createQuery(cu);
        tq.executeUpdate();
    }

    private <T, X> void setAttr(SingularAttribute<T, X> attr, CriteriaUpdate<T> cu, Map<String, Object> values) {
        Expression<X> expr = emf.getCriteriaBuilder().literal(values.get(attr.getName())).as(attr.getJavaType());
        cu.set(attr, expr);
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
