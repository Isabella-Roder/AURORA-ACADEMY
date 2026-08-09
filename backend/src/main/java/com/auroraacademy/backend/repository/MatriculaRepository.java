package com.auroraacademy.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.models.Matricula;
import java.time.LocalDateTime;
import com.auroraacademy.backend.enums.StatusMatricula;



public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    
    List<Matricula> findByAlunoId(Long alunoId);

    List<Matricula> findByCursoId(Long cursoId);

    List<Matricula> findByDataMatricula(LocalDateTime dataMatricula);

    List<Matricula> findByStatusMatricula(StatusMatricula statusMatricula);

    boolean existsByAlunoIdAndCursoId(Long alunoId, Long cursoId);

    boolean existsByAlunoIdAndCursoIdAndStatusMatricula(
        Long alunoId,
        Long cursoId,
        StatusMatricula statusMatricula
    );
}
