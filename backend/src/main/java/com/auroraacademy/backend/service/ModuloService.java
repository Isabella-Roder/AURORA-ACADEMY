package com.auroraacademy.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.models.Curso;
import com.auroraacademy.backend.models.Modulo;
import com.auroraacademy.backend.repository.CursoRepository;
import com.auroraacademy.backend.repository.ModuloRepository;

@Service
public class ModuloService {
    
    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;

    public ModuloService(ModuloRepository moduloRepository, CursoRepository cursoRepository) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Modulo> listar() {
        return moduloRepository.findAll();
    }

    public Modulo buscarPorId(Long id) {
        return moduloRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Modulo nao encontrado."));
    }

    public List<Modulo> listarPorCurso(Long cursoId) {
        return moduloRepository.findByCursoId(cursoId);
    }

    private void validacoes(Modulo modulo) {
        if (modulo.getTitulo() == null || modulo.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Titulo obirgatorio.");
        } else if (modulo.getOrdem() == null) {
            throw new IllegalArgumentException("Ordem obrigatorio.");
        } else if (modulo.getCurso() == null || modulo.getCurso().getId() == null) {
            throw new IllegalArgumentException("Curso obrigatorio.");
        }
    }

    public Modulo cadastrar(Modulo modulo) {
        validacoes(modulo);

        Curso curso = cursoRepository.findById(modulo.getCurso().getId())
            .orElseThrow(() -> new IllegalArgumentException("Curso nao encontrado."));

        modulo.setCurso(curso);

        return moduloRepository.save(modulo);
    }

    public Modulo atualizar(Long id, Modulo dadosAtualizados) {
        Modulo modulo = buscarPorId(id);

        Curso curso = cursoRepository.findById(modulo.getCurso().getId())
            .orElseThrow(() -> new IllegalArgumentException("Curso nao encontrado."));

        modulo.setCurso(curso);
        modulo.setTitulo(dadosAtualizados.getTitulo());
        modulo.setOrdem(dadosAtualizados.getOrdem());

        validacoes(modulo);

        return moduloRepository.save(modulo);
    }

    public void deletar(Long id) {
        Modulo modulo = buscarPorId(id);

        moduloRepository.delete(modulo);
    }
}
