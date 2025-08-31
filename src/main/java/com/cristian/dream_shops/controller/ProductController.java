package com.cristian.dream_shops.controller;

import com.cristian.dream_shops.exceptions.ResourceNotFoundException;
import com.cristian.dream_shops.model.Product;
import com.cristian.dream_shops.request.AddProductRequest;
import com.cristian.dream_shops.request.UpdateProductRequest;
import com.cristian.dream_shops.response.APIResponse;
import com.cristian.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping(path = "/all")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new APIResponse("Success", products));
    }

    @GetMapping(path = "/product/{productId}/product")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new APIResponse("Success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<APIResponse> createProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Success", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping(path = "/product/{id}/update")
    public ResponseEntity<APIResponse> updateProduct(
            @RequestBody UpdateProductRequest product,
            @PathVariable Long id
    ) {
        try {
            Product theProduct = productService.updateProduct(product, id);
            return ResponseEntity.ok(new APIResponse("Success", theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(path = "/product/{id}/delete")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new APIResponse("Success", id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(path = "/products/by/brand-and-name")
    public ResponseEntity<APIResponse> getProductByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name
    ) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new APIResponse("No product found", null));
            }

            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(path = "/products/by/category-and-brand")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrand(
            @RequestParam String category,
            @RequestParam String brand
    ) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new APIResponse("No products found", null));
            }
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(path = "/products/{name}/products")
    public ResponseEntity<APIResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new APIResponse("No product found", null));
            }
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("error", e.getMessage()));
        }
    }

    @GetMapping(path = "/product/by-brand")
    public ResponseEntity<APIResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new APIResponse("No product found", null));
            }
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("error", e.getMessage()));
        }
    }

    @GetMapping(path = "/product/{category}/all/products")
    public ResponseEntity<APIResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new APIResponse("No product found", null));
            }
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("error", e.getMessage()));
        }
    }
}
