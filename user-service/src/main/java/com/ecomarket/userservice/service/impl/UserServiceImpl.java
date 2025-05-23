package com.ecomarket.userservice.service.impl;

import com.ecomarket.userservice.exception.DuplicateResourceException;
import com.ecomarket.userservice.exception.ResourceNotFoundException;
import com.ecomarket.userservice.exception.UnauthorizedException;
import com.ecomarket.userservice.model.User;
import com.ecomarket.userservice.model.dto.UserCreationDto;
import com.ecomarket.userservice.model.dto.UserDto;
import com.ecomarket.userservice.reporistory.UserRepository;
import com.ecomarket.userservice.service.UserService;
import com.ecomarket.userservice.util.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;
    
    @Override
    public UserDto createUser(UserCreationDto userCreationDto) {
        // Verificar si el nombre de usuario ya existe
        if (userRepository.existsByUsername(userCreationDto.getUsername())) {
            throw new DuplicateResourceException("El nombre de usuario ya está en uso: " + userCreationDto.getUsername());
        }
        
        // Verificar si el correo electrónico ya existe
        if (userRepository.existsByEmail(userCreationDto.getEmail())) {
            throw new DuplicateResourceException("El correo electrónico ya está en uso: " + userCreationDto.getEmail());
        }
        
        // Crear nuevo usuario
        User user = new User();
        user.setFirstName(userCreationDto.getFirstName());
        user.setLastName(userCreationDto.getLastName());
        user.setUsername(userCreationDto.getUsername());
        user.setEmail(userCreationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        user.setRoleId(userCreationDto.getRoleId());
        user.setStoreId(userCreationDto.getStoreId());
        user.setStatus("ACTIVE");
        user.setRegistrationDate(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        
        // Convertir a DTO
        return convertToDto(savedUser);
    }
    
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        // Verificar si se está cambiando el nombre de usuario y si el nuevo nombre ya existe
        if (!user.getUsername().equals(userDto.getUsername()) && 
            userRepository.existsByUsername(userDto.getUsername())) {
            throw new DuplicateResourceException("El nombre de usuario ya está en uso: " + userDto.getUsername());
        }
        
        // Verificar si se está cambiando el correo y si el nuevo correo ya existe
        if (!user.getEmail().equals(userDto.getEmail()) && 
            userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("El correo electrónico ya está en uso: " + userDto.getEmail());
        }
        
        // Actualizar usuario
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRoleId(userDto.getRoleId());
        user.setStoreId(userDto.getStoreId());
        if (userDto.getStatus() != null) {
            user.setStatus(userDto.getStatus());
        }
        
        User updatedUser = userRepository.save(user);
        
        // Convertir a DTO
        return convertToDto(updatedUser);
    }
    
    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        return convertToDto(user);
    }
    
    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
        
        return convertToDto(user);
    }
    
    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con correo electrónico: " + email));
        
        return convertToDto(user);
    }
    
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserDto> getUsersByRole(Long roleId) {
        List<User> users = userRepository.findByRoleId(roleId);
        
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserDto> getUsersByStore(Long storeId) {
        List<User> users = userRepository.findByStoreId(storeId);
        
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        
        userRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public void updateStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        user.setStatus(status);
        userRepository.save(user);
    }
    
    @Override
    public void updateLastAccess(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        user.setLastAccess(LocalDateTime.now());
        userRepository.save(user);
    }
    
    @Override
    public UserDto authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new UnauthorizedException("La cuenta de usuario no está activa");
        }
        
        // Actualizar el último acceso
        user.setLastAccess(LocalDateTime.now());
        userRepository.save(user);
        
        return convertToDto(user);
    }
    
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}