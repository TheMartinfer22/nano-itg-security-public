package dev.nanosync.nanoitgsecuritypublic.controller;

import dev.nanosync.nanoitgsecuritypublic.entity.User;
import dev.nanosync.nanoitgsecuritypublic.entity.dto.UserLoginRequest;
import dev.nanosync.nanoitgsecuritypublic.entity.dto.UserLoginResponse;
import dev.nanosync.nanoitgsecuritypublic.entity.dto.UserRegisterRequest;
import dev.nanosync.nanoitgsecuritypublic.entity.dto.UserRegisterResponse;
import dev.nanosync.nanoitgsecuritypublic.service.AuthenticationService;
import dev.nanosync.nanoitgsecuritypublic.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken( (User) auth.getPrincipal());
        return ResponseEntity.ok(new UserLoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest userRequest) {
        return ResponseEntity.ok().body(this.authenticationService.registerUser(userRequest));
    }
}
