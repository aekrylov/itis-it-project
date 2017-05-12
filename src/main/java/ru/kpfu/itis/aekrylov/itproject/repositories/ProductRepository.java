package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.aekrylov.itproject.entities.Product;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 7:06 PM
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
