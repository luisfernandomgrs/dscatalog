package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    private long nonExistingId = 26L;
    private long existingId = 1L;

    @BeforeEach
    void setup() throws Exception {
        this.nonExistingId = 26L;
        this.existingId = 1L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        //Act
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);
        //Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  {
            Optional<Product> optional = productRepository.findById(nonExistingId);
            optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));

            productRepository.deleteById(1000L);
        });
    }
}
