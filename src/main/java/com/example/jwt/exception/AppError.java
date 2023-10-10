package com.example.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AppError {
    private String message;
    private Instant timestamp;
}