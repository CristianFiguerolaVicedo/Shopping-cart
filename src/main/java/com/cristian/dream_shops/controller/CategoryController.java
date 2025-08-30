package com.cristian.dream_shops.controller;

import com.cristian.dream_shops.exceptions.AlreadyExistsException;
import com.cristian.dream_shops.exceptions.ResourceNotFoundException;
import com.cristian.dream_shops.model.Category;
import com.cristian.dream_shops.response.APIResponse;
import com.cristian.dream_shops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping(path = "/all")
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("Categories found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Error: ", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category name) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new APIResponse("Category added", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(path = "/category/{id}/category")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(path = "/category/{name}/category")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new APIResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(path = "/category/{id}/delete")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping(path = "/category/{id}/update")
    public ResponseEntity<APIResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new APIResponse("Update success", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }


}
