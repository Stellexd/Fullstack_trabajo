package com.ecomarket.userservice.service;

import com.ecomarket.userservice.model.dto.UserCreationDto;
import com.ecomarket.userservice.model.dto.UserDto;

import java.util.List;

public interface UserService {
    
    UserDto createUser(UserCreationDto userCreationDto);
    
    UserDto updateUser(Long id, UserDto userDto);
    
    UserDto getUserById(Long id);
    
    UserDto getUserByUsername(String username);
    
    UserDto getUserByEmail(String email);
    
    List<UserDto> getAllUsers();
    
    List<UserDto> getUsersByRole(Long roleId);
    
    List<UserDto> getUsersByStore(Long storeId);
    
    void deleteUser(Long id);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    void updateStatus(Long id, String status);
    
    void updateLastAccess(Long id);
    
    UserDto authenticateUser(String username, String password);
}