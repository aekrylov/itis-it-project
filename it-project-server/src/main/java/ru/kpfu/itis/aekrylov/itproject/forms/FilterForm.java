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
public class FilterForm implements Specification<Post> {

    private String type;
    private String brand;
    private String model;

    //using wrappers here to check for nulls and avoid spring errors
    private Integer price_low;
    private Integer price_high;
    private Integer cores;

    private Integer author;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrice_low() {
        return price_low;
    }

    public void setPrice_low(Integer price_low) {
        this.price_low = price_low;
    }

    public Integer getPrice_high() {
        return price_high;
    }

    public void setPrice_high(Integer price_high) {
        this.price_high = price_high;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

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
