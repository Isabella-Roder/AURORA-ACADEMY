package com.auroraacademy.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auroraacademy.backend.models.Certificado;

public interface CertificadoRepository extends JpaRepository<Certificado, Long>{
    
    Optional<Certificado> findByAlunoIdAndCursoId(Long alunoId, Long cursoId);

    Optional<Certificado> findByCodigoValidacao(String codigoValidacao);

    List<Certificado> findByAlunoId(Long alunoId);
}
