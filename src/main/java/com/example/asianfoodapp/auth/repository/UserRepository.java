package com.example.asianfoodapp.auth.repository;

import com.example.asianfoodapp.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String username);
    Optional<User> findUserByEmail(String email);
}
