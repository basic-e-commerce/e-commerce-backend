package com.example.ecommercebackend.service.product.category;

import com.example.ecommercebackend.builder.product.category.CategoryBuilder;
import com.example.ecommercebackend.dto.file.CoverImageRequestDto;
import com.example.ecommercebackend.dto.file.ImageRequestDto;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryUpdateDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.category.CategoryRepository;
import com.example.ecommercebackend.service.file.CategoryImageService;
import com.example.ecommercebackend.service.file.CoverImageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryBuilder categoryBuilder;
    private final CategoryImageService categoryImageService;

    public CategoryService(CategoryRepository categoryRepository, CategoryBuilder categoryBuilder, CategoryImageService categoryImageService) {
        this.categoryRepository = categoryRepository;
        this.categoryBuilder = categoryBuilder;

        this.categoryImageService = categoryImageService;
    }

    public Category createCategory(CategoryCreateDto categoryCreateDto) {

        if (categoryCreateDto.getName() == null || categoryCreateDto.getName().isBlank()) {
            throw new BadRequestException("Category name is required.");
        }

        if (categoryRepository.existsByCategoryNameEqualsIgnoreCase(categoryCreateDto.getName())){
            throw new ResourceAlreadyExistException("Category "+ExceptionMessage.ALREADY_EXISTS.getMessage());
        }

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin) {
            Category parentCategory = null;
            if (categoryCreateDto.getParentCategoryId() != 0){
                parentCategory = findCategoryById(categoryCreateDto.getParentCategoryId());
                parentCategory.setSubCategory(false);
                categoryRepository.save(parentCategory);
            }
            Category category = categoryBuilder.CategoryCreateDtoToCategory(categoryCreateDto,admin,admin);
            category.setParentCategory(parentCategory);


            Category saveCategory = categoryRepository.save(category);
            if (parentCategory != null){
                parentCategory.setSubCategory(false);
                categoryRepository.updateCategoryId(parentCategory.getId(),saveCategory.getId());
                categoryRepository.save(parentCategory);
            }

            if (categoryCreateDto.getImage() != null){
                CoverImageRequestDto coverImageRequestDto = new CoverImageRequestDto(categoryCreateDto.getImage());
                CoverImage coverImage = categoryImageService.save(coverImageRequestDto, saveCategory.getId());
                saveCategory.setCoverImage(coverImage);
                categoryRepository.save(saveCategory);
            }

            return saveCategory;
        } else {
                throw new BadRequestException("Authenticated user is not an Admin.");
        }

    }

    public List<Category> findParentCategories() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public Category findCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category " +ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Category deleteCategory(Integer id) {
        Category category = findCategoryById(id);
        if (isHasProduct(category.getId()))
            throw new BadRequestException("Category already has a product.");

        if (!category.isSubCategory())
            throw new BadRequestException("Category is not a subcategory.");

        if (category.getCoverImage() != null){
            categoryImageService.delete(category.getCoverImage().getId());
        }

        categoryRepository.deleteById(category.getId());
        return category;
    }

    public Set<Category> getLeafCategories(Category category) {
        Set<Category> leafCategories = new HashSet<>();
        findLeafCategories(category, leafCategories);
        return leafCategories;
    }

    private void findLeafCategories(Category category, Set<Category> leafCategories) {
        if (category == null) {
            return;
        }

        // Eğer alt kategorisi yoksa, bu bir yaprak kategoridir
        if (category.getSubCategories() == null || category.getSubCategories().isEmpty()) {
            leafCategories.add(category);
            return;
        }

        // Alt kategorileri dolaş ve yaprakları ekle
        for (Category subCategory : category.getSubCategories()) {
            findLeafCategories(subCategory, leafCategories);
        }
    }

    public boolean isHasProduct(int id) {
        return categoryRepository.existsByCategoryId(id);
    }

    public Category updateCategory(CategoryUpdateDto categoryUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Admin admin) {
            Category category = findCategoryById(categoryUpdateDto.getId());
            category.setCategoryName(categoryUpdateDto.getName());
            category.setCategoryDescription(categoryUpdateDto.getDescription());
            category.setActive(categoryUpdateDto.isActive());
            category.setUpdatedAt(Instant.now());
            category.setUpdatedBy(admin);
            return categoryRepository.save(category);
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }

    public Category updateCategoryImage(Integer id, MultipartFile image) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Admin admin) {
            Category category = findCategoryById(id);
            if (category.getCoverImage() != null){
                categoryImageService.delete(category.getCoverImage().getId());
                category.setCoverImage(null);
                categoryRepository.save(category);
            }

            ImageRequestDto imageRequestDto = new ImageRequestDto(image);
            CoverImage coverImage = categoryImageService.save(imageRequestDto, category.getId());
            category.setCoverImage(coverImage);
            category.setUpdatedBy(admin);
            category.setUpdatedAt(Instant.now());
            return categoryRepository.save(category);

        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }

    public String deleteCategoryImage(Integer categoryId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Admin admin) {
            Category category = findCategoryById(categoryId);
            if (category.getCoverImage() != null){
                categoryImageService.delete(category.getCoverImage().getId());
                category.setCoverImage(null);
                category.setUpdatedAt(Instant.now());
                category.setUpdatedBy(admin);
                categoryRepository.save(category);
                return "Category image deleted successfully";
            }else
                throw new BadRequestException("Resource Not Found.");
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }
}
