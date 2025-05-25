package com.example.ecommercebackend.repository.product.category;

import com.example.ecommercebackend.entity.product.category.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    boolean existsByCategoryNameEqualsIgnoreCase(String name);
    List<Category> findAllByParentCategoryIsNull();

    @Query(value = "SELECT EXISTS (SELECT 1 FROM product_categories WHERE category_id = :categoryId)", nativeQuery = true)
    boolean existsByCategoryId(@Param("categoryId") Integer categoryId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product_categories SET category_id = :newCategoryId WHERE category_id = :oldCategoryId", nativeQuery = true)
    void updateCategoryId(@Param("oldCategoryId") Integer oldCategoryId, @Param("newCategoryId") Integer newCategoryId);

    Boolean existsByCategoryLinkName(String categoryLinkName);
    Optional<Category> findByCategoryLinkName(String categoryLinkName);

}
