package com.auroraacademy.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.enums.NivelCurso;
import com.auroraacademy.backend.enums.PerfilUsuario;
import com.auroraacademy.backend.enums.StatusCurso;
import com.auroraacademy.backend.models.Curso;
import com.auroraacademy.backend.models.Usuario;
import com.auroraacademy.backend.repository.CursoRepository;
import com.auroraacademy.backend.repository.UsuarioRepository;

@Service
public class CursoService {
    
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public CursoService(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Curso nao encontrado."));
    }

    public List<Curso> listarPorNivel(NivelCurso nivelCurso) {
        return cursoRepository.findByNivelCurso(nivelCurso);
    } 

    public List<Curso> listarPorStatus(StatusCurso statusCurso) {
        return cursoRepository.findByStatusCurso(statusCurso);
    }

    public List<Curso> listarPorProfessorId(Long professorId) {
        return cursoRepository.findByProfessorId(professorId);
    }

    private void validacao(Curso curso) {
        if (curso.getTitulo() == null || curso.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Titulo é obrigátorio.");
        } else if (curso.getDescricao() == null || curso.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatorio.");
        } else if (curso.getCategoria() == null || curso.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria é obrigatorio.");
        } else if (curso.getNivelCurso() == null) {
            throw new IllegalArgumentException("Nivel do curso é obrigatorio.");
        } else if (curso.getPreco() == null) {
            throw new IllegalArgumentException("Preço é obrigatorio.");
        } else if (curso.getImgUrl() == null || curso.getImgUrl().isBlank()) {
            throw new IllegalArgumentException("Url da imagem é obrigatorio.");
        } else if (curso.getStatusCurso() == null) {
            throw new IllegalArgumentException("Status é obrigatorio.");
        } else if (curso.getProfessor() == null || curso.getProfessor().getId() == null) {
            throw new IllegalArgumentException("Professor é obrigatorio.");
        }
    }

    public Curso cadastrar(Curso curso) {
        validacao(curso);

        Usuario professor = usuarioRepository.findById(curso.getProfessor().getId())
            .orElseThrow(() -> new IllegalArgumentException("Professor nao encontrado."));

        if (professor.getPerfil() != PerfilUsuario.PROFESSOR) {
            throw new IllegalArgumentException("Usuario informado nao e professor.");
        }

        curso.setProfessor(professor);
        curso.setDataCriacao(LocalDate.now());

        return cursoRepository.save(curso);
    }

    public Curso atualizar(Long id, Curso cursoAtualizado) {
        Curso curso = buscarPorId(id);

        curso.setTitulo(cursoAtualizado.getTitulo());
        curso.setDescricao(cursoAtualizado.getDescricao());
        curso.setCategoria(cursoAtualizado.getCategoria());
        curso.setNivelCurso(cursoAtualizado.getNivelCurso());
        curso.setCargaHoraria(cursoAtualizado.getCargaHoraria());
        curso.setPreco(cursoAtualizado.getPreco());
        curso.setImgUrl(cursoAtualizado.getImgUrl());
        curso.setStatusCurso(cursoAtualizado.getStatusCurso());
        curso.setProfessor(cursoAtualizado.getProfessor());
        curso.setDataCriacao(cursoAtualizado.getDataCriacao());

        validacao(curso);

        return cursoRepository.save(curso);
    }

    public void deletar(Long id) {
        Curso curso = buscarPorId(id);

        cursoRepository.delete(curso);
    }
}
