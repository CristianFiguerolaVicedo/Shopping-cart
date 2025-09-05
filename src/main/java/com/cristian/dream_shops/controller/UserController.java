package com.cristian.dream_shops.controller;

import com.cristian.dream_shops.response.APIResponse;
import com.cristian.dream_shops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    public ResponseEntity<APIResponse>
}
