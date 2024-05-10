package dev.nanosync.nanoitgsecuritypublic.entity.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
