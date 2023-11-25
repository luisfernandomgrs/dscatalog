package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;
    private long existingId;
    private long nomExistingId;
    private long dependentId;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nomExistingId = 26L;
        dependentId = 4L;
        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(nomExistingId);
        Mockito.doThrow(DatabaseException.class).when(repository).deleteById(dependentId);
    }
    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(260L);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(260L);
    }
    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists() {
        /**
         * This Exception is throws because the settings into mockito...
         * Ex.: Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(nomExistingId);
         *
         * With another values into the parameter, no exception will be throws.
         */
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nomExistingId);
        });
    }
    @Test
    public void deleteShouldThrowsDatabaseExceptionWhenDependentId() {
        /**
         * This Exception is throws because the settings into mockito...
         * Ex.: Mockito.doThrow(DatabaseException.class).when(repository).deleteById(dependentId);
         *
         * With another values into the parameter, no exception will be throws.
         */
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }
}
