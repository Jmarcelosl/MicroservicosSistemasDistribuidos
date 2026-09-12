package com.ecommerce.product_api.repository;

import com.ecommerce.product_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //selecionar um produto por categoria
    @Query("SELECT p FROM product p WHERE p.category.id = :categoryId")
    List<Product> getProductByCategory(@Param("categoryId") Long categoryId);


    //selecionar um produto pelo seu id
    public Product findByProductIdentifier( String productIdentifier);

}

