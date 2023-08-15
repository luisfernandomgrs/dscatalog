package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        /*
        //first model...
        return this.repository.findAll();
        */

        /*
        //second model
        List<Category> categories = this.repository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOs.add(new CategoryDTO(category));
        }
        return categoryDTOs;
        */

        //using lambda functions
        return this.repository.findAll().stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> optional = this.repository.findById(id);
        return new CategoryDTO(optional.orElseThrow(() -> new EntityNotFoundException("Entity not found")));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dtoParam) {
        Category entity = new Category();
        entity.setName(dtoParam.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
}
