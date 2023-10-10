package com.example.jwt.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Authoriry implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}