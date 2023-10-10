package com.example.jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "users", schema = "public")
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @ElementCollection
    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints =
    @UniqueConstraint(name = "user_authoriry_constraint", columnNames = {"user_id", "authoriry"}))
    @Column(name = "authoriry")
    @Enumerated(EnumType.STRING)
    private Set<Authoriry> aurhorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return aurhorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public Set<Authoriry> getAurhorities() {
        return aurhorities;
    }
}