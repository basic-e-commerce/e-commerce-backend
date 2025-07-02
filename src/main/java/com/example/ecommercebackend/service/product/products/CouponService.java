package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.products.coupon.CouponCreateDto;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.products.CouponRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.user.CustomerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final CustomerCouponService customerCouponService;

    public CouponService(CouponRepository couponRepository, CustomerRepository customerRepository, ProductService productService, CustomerCouponService customerCouponService) {
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
        this.customerCouponService = customerCouponService;
    }

    @Transactional
    public Coupon createCoupon(@NotNullParam CouponCreateDto couponCreateDto){
        if (existByCode(couponCreateDto.getCode()))
            throw new ResourceAlreadyExistException("Bu kupon kodu kullanılmaktadır.");

        Coupon coupon = new Coupon();
        coupon.setCode(couponCreateDto.getCode());
        coupon.setDescription(couponCreateDto.getDescription());

        if (couponCreateDto.getDiscountType().equalsIgnoreCase("PERCENTAGE")) {
            BigDecimal discountValue = couponCreateDto.getDiscountValue();
            if (discountValue.compareTo(BigDecimal.ZERO) < 0 || discountValue.compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new BadRequestException("Sadece 0-100 arası değer seçebilirsiniz.");
            }else{
                coupon.setDiscountType(Coupon.DiscountType.valueOf(couponCreateDto.getDiscountType()));
                coupon.setDiscountValue(discountValue);
            }

        } else if (couponCreateDto.getDiscountType().equalsIgnoreCase("FIXEDAMOUNT")) {
            BigDecimal discountValue = couponCreateDto.getDiscountValue();
            if (discountValue.compareTo(BigDecimal.ZERO) < 0 ) {
                throw new BadRequestException("Sabit indirim miktarı 0 birimden düşük olamaz");
            }else {
                coupon.setDiscountType(Coupon.DiscountType.valueOf(couponCreateDto.getDiscountType()));
                coupon.setDiscountValue(discountValue);
            }
        } else
            throw new BadRequestException("Geçersiz İndirim tipi! Lütfen PERCENTAGE yada FIXEDAMOUNT seçiniz!");


        if (couponCreateDto.getTatalUsageLimit()<=0){
            throw new BadRequestException("toplam kullanım limiti 0 dan yüksek değer olmalıdır");
        }else
            coupon.setTotalUsageLimit(couponCreateDto.getTatalUsageLimit());

        if (couponCreateDto.getMinOrderAmountLimit() != null &&
                couponCreateDto.getMinOrderAmountLimit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Minimum sepet tutarı 0 dan büyük olmak zorundadır!");
        }else
            coupon.setMinOrderAmountLimit(couponCreateDto.getMinOrderAmountLimit());

        Instant now = Instant.now();
        Instant startDate = couponCreateDto.getStartDate();
        Instant endDate = couponCreateDto.getEndDate();

        // Başlangıç tarihi geçmiş olamaz
        if (startDate.isBefore(now)) {
            throw new BadRequestException("Kuponun başlangıç tarihi geçmiş olamaz.");
        }

        // Bitiş tarihi geçmiş olamaz
        if (endDate.isBefore(now)) {
            throw new BadRequestException("Kuponun bitiş tarihi geçmiş olamaz.");
        }

        // Bitiş tarihi, başlangıç tarihinden önce veya aynı olamaz
        if (!endDate.isAfter(startDate)) {
            throw new BadRequestException("Kuponun bitiş tarihi, başlangıç tarihinden sonra olmalıdır.");
        }

        // Setleme
        coupon.setCouponStartDate(startDate);
        coupon.setCouponEndDate(endDate);

        coupon.setActive(couponCreateDto.getActive());

        Set<Product> products = new HashSet<>();

        if (couponCreateDto.getProductIds() == null || couponCreateDto.getProductIds().isEmpty()){
            products.addAll(productService.findAll());
        }else{
            for (Integer productId : couponCreateDto.getProductIds()){
                Product product = productService.findProductById(productId);
                products.add(product);
            }
        }
        coupon.setProducts(products);

        if (!couponCreateDto.getPublic()){
            coupon.setPublic(false);

            if (couponCreateDto.getCustomerIds() != null && !couponCreateDto.getCustomerIds().isEmpty()){
                Set<CustomerCoupon> customerCoupons = new HashSet<>();
                Instant create = Instant.now();
                for (Integer customerId : couponCreateDto.getCustomerIds()){
                    Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new NotFoundException("Seçtiğiniz Müşteri bulunamamıştır!: "+ customerId));
                    CustomerCoupon customerCoupon = new CustomerCoupon(customer,coupon,create);
                    CustomerCoupon saveCustomerCoupon = customerCouponService.save(customerCoupon);
                    customerCoupons.add(saveCustomerCoupon);
                }
                coupon.setCustomerCoupons(customerCoupons);
            }else{
                Set<CustomerCoupon> customerCoupons = new HashSet<>();
                List<Customer> customers = customerRepository.findAll();
                Instant create = Instant.now();
                for (Customer customer : customers){
                    CustomerCoupon customerCoupon = new CustomerCoupon(customer,coupon,create);
                    CustomerCoupon saveCustomerCoupon = customerCouponService.save(customerCoupon);
                    customerCoupons.add(saveCustomerCoupon);
                }
                coupon.setCustomerCoupons(customerCoupons);
            }

        }else{
            coupon.setPublic(true);
            coupon.setCustomerCoupons(null);
        }

        return couponRepository.save(coupon);
    }

    public Coupon findCouponByCodeAndActive(String code, boolean active) {
        return couponRepository.findOne(Specification.where(hasCode(code).and(isActive(active)))).orElse(null);
    }

    public Boolean existByCode(String code){
        return couponRepository.existsByCode(code);
    }


    public Specification<Coupon> isActive(Boolean isActive){
        return (Root<Coupon> root,CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("isActive"), isActive);
    }

    public Specification<Coupon> hasCode(String code) {
        return (Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("code"), code);
    }





}
