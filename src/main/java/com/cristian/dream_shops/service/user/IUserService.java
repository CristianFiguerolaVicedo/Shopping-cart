package com.cristian.dream_shops.service.user;

import com.cristian.dream_shops.model.User;
import com.cristian.dream_shops.request.CreateUserRequest;
import com.cristian.dream_shops.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
