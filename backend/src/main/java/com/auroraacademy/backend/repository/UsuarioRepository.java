package com.auroraacademy.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
}
