package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoificationRepository extends JpaRepository<Notification, Long> {
}
