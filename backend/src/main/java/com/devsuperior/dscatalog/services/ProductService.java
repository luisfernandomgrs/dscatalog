package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        //using lambda functions
        return this.repository.findAll(pageRequest).map(element -> new ProductDTO(element, element.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> optional = this.repository.findById(id);
        return new ProductDTO(optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found")), optional.get().getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dtoParam) {
        Product entity = new Product();
        this.CopyDtoToEntity(dtoParam, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dtoParam) {
        try {
            Product entity = repository.getReferenceById(id);
            this.CopyDtoToEntity(dtoParam, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity, entity.getCategories());
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            Optional<Product> optional = this.repository.findById(id);
            optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));

            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    private void CopyDtoToEntity(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setDate(productDTO.getDate());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());

        product.getCategories().clear();
        for (CategoryDTO categoryDTO : productDTO.getCategories()) {
            //Category category = categoryRepository.getOne(categoryDTO.getId());

            //O uso de getOne(ID) para springframework.data:spring-data-jpa:3.1.2 | "is deprecated"
            Optional<Category> optionalCategory = this.categoryRepository.findById(categoryDTO.getId());
            product.getCategories().add(optionalCategory.get());
        }
    }
}
