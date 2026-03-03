package com.example.shopmyway.dao;

import com.example.shopmyway.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {

//    @Query(value = "SELECT p FROM product_image p WHERE p.product.productId = :productId", nativeQuery = true)
//    Optional<ProductImage> findByProductId(@Param("productId") Integer productId);
    @Query(value = "SELECT * FROM product_image WHERE product_id = :productId", nativeQuery = true)
    Optional<ProductImage> findByProductId(@Param("productId") Integer productId);

}
