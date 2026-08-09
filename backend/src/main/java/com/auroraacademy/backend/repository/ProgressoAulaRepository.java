package com.auroraacademy.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.models.ProgressoAula;

public interface ProgressoAulaRepository extends JpaRepository<ProgressoAula, Long> {
    
    Optional<ProgressoAula> findByAlunoIdAndAulaId(Long alunoId, Long aulaId);

    List<ProgressoAula> findByAlunoIdAndAulaModuloCursoId(Long alunoId, Long cursoId);

    long countByAlunoIdAndAulaModuloCursoIdAndConcluidaTrue(Long alunoId, Long cursoId);

}
