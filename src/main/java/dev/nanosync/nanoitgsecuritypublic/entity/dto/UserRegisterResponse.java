package dev.nanosync.nanoitgsecuritypublic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterResponse {
    private Long id;
    private String email;
    private String password;
}
