package com.example.ecommercebackend.entity.file;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
    private Integer id;

    private String name;
    private Long size; // Dosya boyutu (byte cinsinden)

    public File(String name, Long size) {
        this.name = name;
        this.size = size;
    }
    public File(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
