package com.example.ecommercebackend.repository.product.category;

import com.example.ecommercebackend.entity.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByCategoryNameEqualsIgnoreCase(String name);
    List<Category> findAllByParentCategoryIsNull();

    @Query(value = "SELECT EXISTS (SELECT 1 FROM product_categories WHERE category_id = :categoryId)", nativeQuery = true)
    boolean existsByCategoryId(@Param("categoryId") Integer categoryId);

    @Modifying
    @Query(value = "UPDATE product_categories SET category_id = :newCategoryId WHERE category_id = :oldCategoryId", nativeQuery = true)
    void updateCategoryId(@Param("oldCategoryId") Long oldCategoryId, @Param("newCategoryId") Long newCategoryId);


}
