package com.example.ecommercebackend.service.product.category;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.product.category.CategoryBuilder;
import com.example.ecommercebackend.dto.file.CoverImageRequestDto;
import com.example.ecommercebackend.dto.file.ImageRequestDto;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryDetailDto;
import com.example.ecommercebackend.dto.product.category.CategoryUpdateDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.category.CategoryRepository;
import com.example.ecommercebackend.service.file.CategoryImageService;
import com.example.ecommercebackend.service.file.CoverImageService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
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

    public CategoryDetailDto createCategory(@NotNullParam CategoryCreateDto categoryCreateDto) {

        if (categoryCreateDto.getName() == null || categoryCreateDto.getName().isBlank()) {
            throw new BadRequestException("Category name is required.");
        }

        if (findByCategoryNameEqualIgnore(categoryCreateDto.getName()) != null){
            throw new ResourceAlreadyExistException("Category "+ExceptionMessage.ALREADY_EXISTS.getMessage());
        }


        Category parentCategory = null;
        if (categoryCreateDto.getParentCategoryId() != 0){
            parentCategory = findCategoryById(categoryCreateDto.getParentCategoryId());
            parentCategory.setSubCategory(false);
            categoryRepository.save(parentCategory);
        }
        Category category = categoryBuilder.CategoryCreateDtoToCategory(categoryCreateDto);
        category.setParentCategory(parentCategory);
        category.setCategoryLinkName(generateLinkName(categoryCreateDto.getName()));


        Category saveCategory = categoryRepository.save(category);
        if (parentCategory != null){
            parentCategory.setSubCategory(false);
            categoryRepository.save(parentCategory);
            categoryRepository.updateCategoryId(parentCategory.getId(),saveCategory.getId());
        }

        if (categoryCreateDto.getImage() != null){
            CoverImageRequestDto coverImageRequestDto = new CoverImageRequestDto(categoryCreateDto.getImage());
            CoverImage coverImage = categoryImageService.save(coverImageRequestDto, saveCategory.getId());
            saveCategory.setCoverImage(coverImage);
            categoryRepository.save(saveCategory);
        }

        return categoryBuilder.categoryToCategoryDetailDto(saveCategory);


    }

    public List<Category> findParentCategories() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public Category findCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category " +ExceptionMessage.NOT_FOUND.getMessage()));
    }
    public Category findCategoryByLinkName(String linkName){
        return categoryRepository.findByCategoryLinkName(linkName).orElseThrow(()-> new NotFoundException("Category " +ExceptionMessage.NOT_FOUND.getMessage()));
    }
    public Category findByCategoryNameEqualIgnore(String name){
        return categoryRepository.findOne(Specification.where(hasNameEqualIgnoreCase(name))).orElse(null);
    }

    public CategoryDetailDto deleteCategory(@NotNullParam Integer id) {
        Category category = findCategoryById(id);
        if (isHasProduct(category.getId()))
            throw new BadRequestException("Category already has a product.");

        if (!category.isSubCategory())
            throw new BadRequestException("Category is not a subcategory.");

        if (category.getCoverImage() != null){
            categoryImageService.delete(category.getCoverImage().getId());
        }

        categoryRepository.deleteById(category.getId());
        if (category.getParentCategory() != null){
            Category parentCategory = category.getParentCategory();
            parentCategory.setSubCategory(true);
            categoryRepository.save(parentCategory);
        }


        return categoryBuilder.categoryToCategoryDetailDto(category);
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

    public CategoryDetailDto updateCategory(@NotNullParam CategoryUpdateDto categoryUpdateDto) {
        Category category = findCategoryById(categoryUpdateDto.getId());

        if (!category.getCategoryName().equals(categoryUpdateDto.getName())) {
            category.setCategoryName(categoryUpdateDto.getName());
            String linkName = generateLinkName(categoryUpdateDto.getName());
            if (existCategoryByLinkName(linkName)){
                linkName+="-1";
            }
            category.setCategoryLinkName(linkName);
        }
        category.setCategoryDescription(categoryUpdateDto.getDescription());

        return categoryBuilder.categoryToCategoryDetailDto(categoryRepository.save(category));
    }

    public CategoryDetailDto updateCategoryImage(@NotNullParam Integer id,@NotNullParam MultipartFile image) {

        Category category = findCategoryById(id);
        if (category.getCoverImage() != null){
            categoryImageService.delete(category.getCoverImage().getId());
            category.setCoverImage(null);
            categoryRepository.save(category);
        }


        ImageRequestDto imageRequestDto = new ImageRequestDto(image);
        CoverImage coverImage = categoryImageService.save(imageRequestDto, category.getId());
        category.setCoverImage(coverImage);

        return categoryBuilder.categoryToCategoryDetailDto(categoryRepository.save(category));
    }

    public String deleteCategoryImage(@NotNullParam Integer categoryId) {

        Category category = findCategoryById(categoryId);
        if (category.getCoverImage() != null){
            categoryImageService.delete(category.getCoverImage().getId());
            category.setCoverImage(null);
            categoryRepository.save(category);
            return "Category image deleted successfully";
        }else
            throw new BadRequestException("Resource Not Found.");
    }

    public boolean existCategoryByLinkName(String linkName) {
        return categoryRepository.existsByCategoryLinkName(linkName);
    }

    public Specification<Category> hasNameEqualIgnoreCase(String name) {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                name == null ? null :
                        cb.equal(cb.lower(root.get("categoryName")), name.toLowerCase());
    }


    private String generateLinkName(String name) {
        if (name == null) return "";

        String processedName = name.trim().toLowerCase()
                .replace("ç", "c")
                .replace("ğ", "g")
                .replace("ı", "i")
                .replace("ö", "o")
                .replace("ş", "s")
                .replace("ü", "u")
                .replaceAll("\\s+", "-");

        String finalLinkName = processedName;
        int counter = 1;

        // Zaten linkName kontrolünü yapan metodunu kullanalım
        while (existCategoryByLinkName(finalLinkName)) {
            finalLinkName = processedName + "-" + counter;
            counter++;
        }

        return finalLinkName;
    }

    public CategoryDetailDto getCategoryByLinkName(String linkName) {
        return categoryBuilder.categoryToCategoryDetailDto(findCategoryByLinkName(linkName));
    }
}
