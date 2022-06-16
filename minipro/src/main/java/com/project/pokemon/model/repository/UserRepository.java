package com.project.pokemon.model.repository;

import com.project.pokemon.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);
    Optional<Users> findByNickname (String nickname);
    Optional<Users> findByEmail (String email);
    Optional<Users> findByEmailAndPassword (String email, String pw_sha256);
}