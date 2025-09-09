package com.cristian.dream_shops.controller;

import com.cristian.dream_shops.exceptions.ResourceNotFoundException;
import com.cristian.dream_shops.model.Cart;
import com.cristian.dream_shops.model.User;
import com.cristian.dream_shops.repository.CartItemRepository;
import com.cristian.dream_shops.repository.UserRepository;
import com.cristian.dream_shops.response.APIResponse;
import com.cristian.dream_shops.service.cart.ICartItemService;
import com.cristian.dream_shops.service.cart.ICartService;
import com.cristian.dream_shops.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping(path = "${api.prefix}/cartItems")
@RequiredArgsConstructor
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping(path = "/item/add")
    public ResponseEntity<APIResponse> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new APIResponse("Item added to cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(path = "/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<APIResponse> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId
    ) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new APIResponse("Remove item success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping(path = "/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<APIResponse> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Quantity update success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }
}
