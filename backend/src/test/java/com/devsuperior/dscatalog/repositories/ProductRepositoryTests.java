package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;
    private long nonExistingId;
    private long existingId;
    private long countTotalProducts;

    @BeforeEach
    void setup() throws Exception {
        this.nonExistingId = 26L;
        this.existingId = 1L;
        this.countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        //Act
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);
        //Assert
        Assertions.assertFalse(result.isPresent());
    }
    @Test
    public void saveShouldpersistWithAutoIncrementWhenIdIsNull() {
        Product entity = Factory.createProduct();
        entity.setId(null);

        entity = repository.save(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(++this.countTotalProducts, entity.getId());
    }
    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  {
            Optional<Product> entity = repository.findById(nonExistingId);
            entity.orElseThrow(() -> new ResourceNotFoundException("Id not found"));

            //productRepository.deleteById(1000L);
            /**
             * The method delete get the before class from the findById,
             * instead of make a full search to delete record
             */
            repository.delete(entity.get());
        });
    }

    @Test
    public void findByIdShouldReturnAnValidOptionalWhenIdExists() {
        //Act
        Optional<Product> entity = repository.findById(existingId);
        //Assert
        Assertions.assertTrue(entity.isPresent());
    }

    @Test
    public void findByIdShouldReturnAnEmptyOptionalWhenIdDoesNotExists() {
        //Act
        Optional<Product> entity = repository.findById(nonExistingId);
        //Assert
        Assertions.assertTrue(entity.isEmpty());
    }
}
