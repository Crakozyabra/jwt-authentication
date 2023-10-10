package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username: {}", username);
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return user;
    }
}