package com.example.jwt.repository;

import com.example.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    @Query("FROM User u LEFT JOIN FETCH u.aurhorities WHERE u.username = :un")
    Optional<User> findByUsername(@Param("un") String username);
}