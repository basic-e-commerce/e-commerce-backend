package com.example.ecommercebackend.category;

import com.example.ecommercebackend.SecurityTestHelper;
import com.example.ecommercebackend.builder.product.category.CategoryBuilder;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryDetailDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationRequestDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationResponseDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.category.CategoryRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.AdminRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import com.example.ecommercebackend.service.auth.AuthenticationService;
import com.example.ecommercebackend.service.file.CategoryImageService;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Gerçek test DB kullan
@ActiveProfiles("test") // application-test.yml veya .properties profili üzerinden çalışır
public class CategoryServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void setup(@Autowired CustomerRepository customerRepository,
                      @Autowired AdminRepository adminRepository,
                      @Autowired RoleRepository roleRepository,
                      @Autowired CountryRepository countryRepository,
                      @Autowired PasswordEncoder passwordEncoder) {

        if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
            roleRepository.save(new Role("ADMIN"));
        }

        if (roleRepository.findByRoleName("CUSTOMER").isEmpty()) {
            roleRepository.save(new Role("CUSTOMER"));
        }

        if (countryRepository.findByUpperName("TURKIYE").isEmpty()) {
            countryRepository.save(new Country("TR", "Türkiye", "TURKIYE", "TUR", (short) 792, 90));
        }

        if (customerRepository.findByUsername("customer@gmail.com").isEmpty()) {
            customerRepository.save(new Customer(
                    "customer", "customerSoyAd", "5075678909", "customer@gmail.com",
                    passwordEncoder.encode("pass"),
                    Set.of(roleRepository.findByRoleName("CUSTOMER").get()),
                    true, true
            ));
        }

        if (adminRepository.findByUsername("admin@gmail.com").isEmpty()) {
            adminRepository.save(new Admin(
                    "admin", "admin", "56789089786", "admin@gmail.com",
                    passwordEncoder.encode("admin"),
                    Set.of(roleRepository.findByRoleName("ADMIN").get()),
                    true, true
            ));
        }
    }


    @Test
    void itShouldCreateCategorySuccessfully() {

        SecurityTestHelper.loginAsAdmin();
        // Arrange
        CategoryCreateDto dto = new CategoryCreateDto("Electronics","açıklama",0);
        CategoryDetailDto created = categoryService.createCategory(dto);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());

        Optional<Category> categoryFromDb = categoryRepository.findById(created.getId());
        Assertions.assertTrue(categoryFromDb.isPresent());
        Assertions.assertEquals("Electronics", categoryFromDb.get().getCategoryName());

    }

}