package com.cristian.dream_shops.controller;

import com.cristian.dream_shops.exceptions.ResourceNotFoundException;
import com.cristian.dream_shops.model.Cart;
import com.cristian.dream_shops.response.APIResponse;
import com.cristian.dream_shops.service.cart.ICartService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "${api.prefix}/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping(path = "/{id}/my-cart")
    public ResponseEntity<APIResponse> getCart(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCart(id);
            return ResponseEntity.ok(new APIResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(path = "/{id}/clear")
    public ResponseEntity<APIResponse> clearCart(@PathVariable Long id) {
        try {
            cartService.clearCart(id);
            return ResponseEntity.ok(new APIResponse("Clear cart success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(path = "/{id}/cart/total-price")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long id) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(id);
            return ResponseEntity.ok(new APIResponse("Total price: ", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }
}
