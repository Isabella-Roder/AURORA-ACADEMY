package com.auroraacademy.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.enums.PerfilUsuario;
import com.auroraacademy.backend.enums.StatusCurso;
import com.auroraacademy.backend.enums.StatusMatricula;
import com.auroraacademy.backend.models.Curso;
import com.auroraacademy.backend.models.Matricula;
import com.auroraacademy.backend.models.Usuario;
import com.auroraacademy.backend.repository.CursoRepository;
import com.auroraacademy.backend.repository.MatriculaRepository;
import com.auroraacademy.backend.repository.UsuarioRepository;

@Service
public class MatriculaService {
    
    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Matricula> listar() {
        return matriculaRepository.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Matricula nao encontrada."));
    }

    public List<Matricula> listarPorAluno(Long alunoId) {
        return matriculaRepository.findByAlunoId(alunoId);
    }

    public List<Matricula> listarPorCurso(Long cursoId) {
        return matriculaRepository.findByCursoId(cursoId);
    }

    public List<Matricula> listarPorDataMatricula(LocalDateTime dataMatricula) {
        return matriculaRepository.findByDataMatricula(dataMatricula);
    }

    public List<Matricula> listarPorStatus(StatusMatricula statusMatricula) {
        return matriculaRepository.findByStatusMatricula(statusMatricula);
    }

    private void validacoes(Matricula matricula) {
        if (matricula.getAluno() == null || matricula.getAluno().getId() == null) {
            throw new IllegalArgumentException("Aluno obrigatorio.");
        } else if (matricula.getCurso() == null || matricula.getCurso().getId() == null) {
            throw new IllegalArgumentException("Curso obrigatorio.");
        }
    }

    public Matricula matricular(Matricula matricula) {
        validacoes(matricula);

        Usuario aluno = usuarioRepository.findById(matricula.getAluno().getId())
            .orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado."));
        
        Curso curso = cursoRepository.findById(matricula.getCurso().getId())
            .orElseThrow(() -> new IllegalArgumentException("Curso nao encontrado."));

        if (aluno.getPerfil() != PerfilUsuario.ALUNO) {
            throw new IllegalArgumentException("Usuario informado nao e aluno");
        }

        if (curso.getStatusCurso() != StatusCurso.PUBLICADO) {
            throw new IllegalArgumentException("Curso nao encontrado.");
        }

        if (matriculaRepository.existsByAlunoIdAndCursoId(aluno.getId(), curso.getId())) {
            throw new IllegalArgumentException("Aluno ja matriculado neste curso.");
        }
        
        matricula.setAluno(aluno);
        matricula.setCurso(curso);
        matricula.setDataMatricula(LocalDateTime.now());
        matricula.setStatusMatricula(StatusMatricula.ATIVA);

        return matriculaRepository.save(matricula);
    }

    public Matricula cancelar(Long id) {
        Matricula matricula = buscarPorId(id);

        matricula.setStatusMatricula(StatusMatricula.CANCELADA);

        return matriculaRepository.save(matricula);
    }
}
