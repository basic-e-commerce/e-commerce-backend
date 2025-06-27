package com.example.ecommercebackend;

import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.repository.product.category.CategoryRepository;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CategoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldReturnCategoryById(){
        Category category = new Category("test1", "Test1");
        category.setId(1);

        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Category result = categoryService.findCategoryById(1);
        Assertions.assertEquals(category.getCategoryName(), result.getCategoryName());
    }
}
