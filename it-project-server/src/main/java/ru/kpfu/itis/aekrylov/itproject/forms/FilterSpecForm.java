package ru.kpfu.itis.aekrylov.itproject.forms;

import org.springframework.data.jpa.domain.Specification;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers.isEmpty;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 11:05 PM
 */
public class FilterSpecForm extends FilterForm implements Specification<Post> {

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        Path<?> product = root.get("product");

        if(!isEmpty(type)) {
            predicates.add(cb.equal(product.get("type"), type));
        }

        if(!isEmpty(brand)) {
            predicates.add(cb.like(cb.lower(product.get("brand")), "%"+brand.toLowerCase()+"%"));
        }

        if(!isEmpty(model)) {
            predicates.add(cb.like(cb.lower(product.get("model")), "%"+model.toLowerCase()+"%"));
        }

        if(price_low != null) {
            predicates.add(cb.ge(product.get("price"), price_low));
        }
        if(price_high != null) {
            predicates.add(cb.le(product.get("price"), price_high));
        }

        if(cores != null) {
            predicates.add(cb.equal(product.get("cores"), cores));
        }

        if(author != null) {
            predicates.add(cb.equal(root.get("user").get("id"), author));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
