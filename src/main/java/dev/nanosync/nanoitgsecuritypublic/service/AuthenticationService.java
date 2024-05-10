package dev.nanosync.nanoitgsecuritypublic.service;

import dev.nanosync.nanoitgsecuritypublic.entity.User;
import dev.nanosync.nanoitgsecuritypublic.entity.dto.UserRegisterRequest;
import dev.nanosync.nanoitgsecuritypublic.entity.dto.UserRegisterResponse;
import dev.nanosync.nanoitgsecuritypublic.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        if(userRepository.findByEmail(userRegisterRequest.getEmail()) != null){
            throw new RuntimeException("O e-mail já está em uso.");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setEmail(userRegisterRequest.getEmail());
//        BeanUtils.copyProperties(userRegisterRequest, user);
        User savedUser = userRepository.save(user);
        return new UserRegisterResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getPassword());
    }

}
