package com.example.ecommercebackend.category;

import com.example.ecommercebackend.builder.product.category.CategoryBuilder;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryDetailDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.category.CategoryRepository;
import com.example.ecommercebackend.service.file.CategoryImageService;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private CategoryBuilder categoryBuilder;
//
//    @Mock
//    private CategoryImageService categoryImageService;
//
//    @InjectMocks
//    private CategoryService categoryService;
//
//    @Test
//    void itShouldCreateCategorySuccessfully() {
//        // Arrange
//        CategoryCreateDto dto = new CategoryCreateDto("Electronics","açıklama",0);
//        CategoryDetailDto created = categoryService.createCategory(dto);
//
//        Assertions.assertNotNull(created);
//        Assertions.assertNotNull(created.getId());
//
//        Optional<Category> categoryFromDb = categoryRepository.findById(created.getId());
//        Assertions.assertTrue(categoryFromDb.isPresent());
//        Assertions.assertEquals("Electronics", categoryFromDb.get().getCategoryName());
//
//    }

}