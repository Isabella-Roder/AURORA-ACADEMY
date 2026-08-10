package com.auroraacademy.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.enums.PerfilUsuario;
import com.auroraacademy.backend.enums.StatusMatricula;
import com.auroraacademy.backend.models.Aula;
import com.auroraacademy.backend.models.Matricula;
import com.auroraacademy.backend.models.ProgressoAula;
import com.auroraacademy.backend.models.Usuario;
import com.auroraacademy.backend.repository.AulaRepository;
import com.auroraacademy.backend.repository.MatriculaRepository;
import com.auroraacademy.backend.repository.ProgressoAulaRepository;
import com.auroraacademy.backend.repository.UsuarioRepository;

@Service
public class ProgressoAulaService {
    
    private final ProgressoAulaRepository progressoAulaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AulaRepository aulaRepository;
    private final MatriculaRepository matriculaRepository;
    private final CertificadoService certificadoService;

    public ProgressoAulaService(ProgressoAulaRepository progressoAulaRepository, UsuarioRepository usuarioRepository, AulaRepository aulaRepository, MatriculaRepository matriculaRepository, CertificadoService certificadoService) {
        this.progressoAulaRepository = progressoAulaRepository;
        this.usuarioRepository = usuarioRepository;
        this.aulaRepository = aulaRepository;
        this.matriculaRepository = matriculaRepository;
        this.certificadoService = certificadoService;
    }

    private void atualizarStatusMatricula(Long alunoId, Long cursoId) {
        long totalAulas = aulaRepository.countByModuloCursoId(cursoId);

        long aulasConcluidas = progressoAulaRepository.countByAlunoIdAndAulaModuloCursoIdAndConcluidaTrue(alunoId, cursoId);

        Matricula matricula = matriculaRepository.findByAlunoIdAndCursoId(alunoId, cursoId)
            .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));

        if (matricula.getStatusMatricula() == StatusMatricula.CANCELADA) {
            return;
        }

        boolean cursoConcluido = totalAulas > 0 && aulasConcluidas == totalAulas;

        matricula.setStatusMatricula(
            cursoConcluido
                ? StatusMatricula.CONCLUIDA
                : StatusMatricula.ATIVA  
        );

        matriculaRepository.save(matricula);

        if (cursoConcluido) {
            certificadoService.gerar(alunoId, cursoId);
        }
    }

    public ProgressoAula marcarConcluida(Long alunoId, Long aulaId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        if (aluno.getPerfil() != PerfilUsuario.ALUNO) {
            throw new IllegalArgumentException("O usuario não é um aluno.");
        }

        Aula aula = aulaRepository.findById(aulaId)
            .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada."));

        Long cursoId = aula.getModulo().getCurso().getId();

        boolean matriculasAtivas = matriculaRepository.existsByAlunoIdAndCursoIdAndStatusMatricula(alunoId, cursoId, StatusMatricula.ATIVA);

        if (!matriculasAtivas) {
            throw new IllegalArgumentException("O aluno não possui matrícula ativa no curso da aula.");
        }

        ProgressoAula progressoAula = progressoAulaRepository.findByAlunoIdAndAulaId(alunoId, aulaId)
            .orElseGet(ProgressoAula::new);

        progressoAula.setAluno(aluno);
        progressoAula.setAula(aula);
        progressoAula.setConcluida(true);
        progressoAula.setDataConclusao(LocalDateTime.now());

        ProgressoAula progressoSalvo = progressoAulaRepository.save(progressoAula);

        atualizarStatusMatricula(alunoId, cursoId);

        return progressoSalvo;
    }

    public void desmarcarConcluida(Long alunoId, Long aulaId) {
        ProgressoAula progresso = progressoAulaRepository.findByAlunoIdAndAulaId(alunoId, aulaId)
            .orElseThrow(() -> new IllegalArgumentException("Progresso da aula não encontrado."));

        Long cursoId = progresso.getAula().getModulo().getCurso().getId();

        progressoAulaRepository.delete(progresso);

        atualizarStatusMatricula(alunoId, cursoId);
    }

    public List<ProgressoAula> listarPorCurso(Long alunoId, Long cursoId) {
        return progressoAulaRepository.findByAlunoIdAndAulaModuloCursoId(alunoId, cursoId);
    }
}
