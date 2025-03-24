package com.example.ecommercebackend.entity.user;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Admin admin;

    private String title;
    private String content;

    private Boolean seen;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant notificationExpiryDate;

    public Notification(Admin admin, String title, String content, Instant notificationExpiryDate) {
        this.admin = admin;
        this.title = title;
        this.content = content;
        this.notificationExpiryDate = notificationExpiryDate;
    }

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getNotificationExpiryDate() {
        return notificationExpiryDate;
    }

    public void setNotificationExpiryDate(Instant notificationExpiryDate) {
        this.notificationExpiryDate = notificationExpiryDate;
    }
}
