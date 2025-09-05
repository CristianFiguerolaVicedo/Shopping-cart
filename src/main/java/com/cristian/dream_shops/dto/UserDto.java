package com.cristian.dream_shops.dto;

import com.cristian.dream_shops.model.Cart;
import com.cristian.dream_shops.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
