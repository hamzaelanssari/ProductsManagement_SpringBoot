package org.products.Repositories;

import org.products.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByDesignation(String designation);
    @Query("select p from Product p where p.designation like :x")
    Page<Product> search(@Param("x")String mc, Pageable pageable);
}