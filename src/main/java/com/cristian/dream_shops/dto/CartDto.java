package com.cristian.dream_shops.dto;

import com.cristian.dream_shops.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
