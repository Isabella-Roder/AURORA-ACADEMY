package com.auroraacademy.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.enums.NivelCurso;
import com.auroraacademy.backend.enums.StatusCurso;
import com.auroraacademy.backend.models.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    List<Curso> findByNivelCurso(NivelCurso nivelCurso);

    List<Curso> findByStatusCurso(StatusCurso statusCurso);

    List<Curso> findByProfessorId(Long ProfessorId);

}
