package com.example.ecommercebackend.repository.merchant;

import com.example.ecommercebackend.entity.merchant.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
