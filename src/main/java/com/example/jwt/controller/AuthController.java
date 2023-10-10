package com.example.jwt.controller;

import com.example.jwt.dto.JwtRequest;
import com.example.jwt.dto.JwtResponse;
import com.example.jwt.exception.AppError;
import com.example.jwt.service.UserService;
import com.example.jwt.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
                    jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.info("BadCredentialsException");
            return new ResponseEntity<>(new AppError("Wrong login or password", Instant.now()),
                    HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}