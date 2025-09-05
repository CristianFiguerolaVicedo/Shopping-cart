package com.cristian.dream_shops.service.user;

import com.cristian.dream_shops.dto.UserDto;
import com.cristian.dream_shops.exceptions.AlreadyExistsException;
import com.cristian.dream_shops.exceptions.ResourceNotFoundException;
import com.cristian.dream_shops.model.User;
import com.cristian.dream_shops.repository.UserRepository;
import com.cristian.dream_shops.request.CreateUserRequest;
import com.cristian.dream_shops.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
       return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLasName());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
