package com.cristian.dream_shops.controller;

import com.cristian.dream_shops.exceptions.AlreadyExistsException;
import com.cristian.dream_shops.exceptions.ResourceNotFoundException;
import com.cristian.dream_shops.model.User;
import com.cristian.dream_shops.request.CreateUserRequest;
import com.cristian.dream_shops.request.UpdateUserRequest;
import com.cristian.dream_shops.response.APIResponse;
import com.cristian.dream_shops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping(path = "/{id}/user")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(new APIResponse("Success", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            return ResponseEntity.ok(new APIResponse("Success", user));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping(path = "/{id}/update")
    public ResponseEntity<APIResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {
        try {
            User user = userService.updateUser(id, request);
            return ResponseEntity.ok(new APIResponse("Success", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new APIResponse("Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }
}
