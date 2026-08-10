package com.auroraacademy.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.enums.StatusMatricula;
import com.auroraacademy.backend.models.Certificado;
import com.auroraacademy.backend.models.Matricula;
import com.auroraacademy.backend.repository.CertificadoRepository;
import com.auroraacademy.backend.repository.MatriculaRepository;

@Service
public class CertificadoService {
    
    private final CertificadoRepository certificadoRepository;
    private final MatriculaRepository matriculaRepository;

    public CertificadoService(CertificadoRepository certificadoRepository, MatriculaRepository matriculaRepository) {
        this.certificadoRepository = certificadoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public Certificado gerar(Long alunoId, Long cursoId) {
        Certificado existente = certificadoRepository.findByAlunoIdAndCursoId(alunoId, cursoId)
            .orElse(null);

        if (existente != null) {
            return existente;
        }

        Matricula matricula = matriculaRepository.findByAlunoIdAndCursoId(alunoId, cursoId)
            .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrado."));

        if (matricula.getStatusMatricula() != StatusMatricula.CONCLUIDA) {
            throw new IllegalArgumentException("O curso ainda não foi concluido.");
        }

        Certificado certificado = new Certificado();
        certificado.setAluno(matricula.getAluno());
        certificado.setCurso(matricula.getCurso());
        certificado.setDataEmissao(LocalDateTime.now());
        certificado.setCodigoValidacao(gerarCodigoValidacao());

        return certificadoRepository.save(certificado);
    }

    public List<Certificado> listarPorAluno(Long alunoId) {
        return certificadoRepository.findByAlunoId(alunoId);
    }

    public Certificado buscarPorCodigo(String codigoValidacao) {
        return certificadoRepository.findByCodigoValidacao(codigoValidacao)
            .orElseThrow(() -> new IllegalArgumentException("Certificado não encontrado."));
    }

    private String gerarCodigoValidacao() {
        return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .toUpperCase();
    }
}
