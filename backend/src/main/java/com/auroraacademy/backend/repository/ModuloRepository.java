package com.auroraacademy.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.models.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    
    List<Modulo> findByCursoId(Long cursoId);

}
