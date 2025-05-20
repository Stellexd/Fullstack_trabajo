package com.ecomarket.userservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, message = "El nombre de usuario debe tener al menos 4 caracteres")
    private String username;
    
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;
    
    @NotNull(message = "El ID de rol es obligatorio")
    private Long roleId;
    
    private Long storeId;
    
    private String status;
    
    private LocalDateTime registrationDate;
    
    private LocalDateTime lastAccess;
}