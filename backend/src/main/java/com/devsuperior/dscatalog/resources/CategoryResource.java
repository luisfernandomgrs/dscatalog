package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        /* Mock without database access...
        List<Category> listCategory = new ArrayList<>();

        listCategory.add(new Category(1L, "Books"));
        listCategory.add(new Category(2L, "Electronics"));
        listCategory.add(new Category(3L, "Mobile"));

        return ResponseEntity.ok(listCategory);
        */
        return ResponseEntity.ok(this.categoryService.findAll());
    }
}
