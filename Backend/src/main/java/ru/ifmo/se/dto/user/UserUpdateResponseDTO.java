package ru.ifmo.se.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateResponseDTO {
    private String token;
    private UserDTO user;
}
