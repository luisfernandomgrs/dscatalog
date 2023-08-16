package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        /* Mock without database access...
        List<Category> listCategory = new ArrayList<>();

        listCategory.add(new Category(1L, "Books"));
        listCategory.add(new Category(2L, "Electronics"));
        listCategory.add(new Category(3L, "Mobile"));

        return ResponseEntity.ok(listCategory);
        */
        return ResponseEntity.ok(this.categoryService.findAll());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dtoParam) {
        CategoryDTO categoryDTO = categoryService.insert(dtoParam);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dtoParam) {
        CategoryDTO categoryDTO = categoryService.update(id, dtoParam);
        return ResponseEntity.ok(categoryDTO);
    }
}
