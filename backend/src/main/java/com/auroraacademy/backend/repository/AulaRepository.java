package com.auroraacademy.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.models.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    
    List<Aula> findByModuloId(Long moduloId);

}
