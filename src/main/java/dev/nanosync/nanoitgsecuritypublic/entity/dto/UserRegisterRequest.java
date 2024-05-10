package dev.nanosync.nanoitgsecuritypublic.entity.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String email;
    private String password;
}
