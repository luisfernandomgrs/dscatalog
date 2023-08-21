package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
