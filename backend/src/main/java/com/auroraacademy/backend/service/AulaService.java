package com.auroraacademy.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.models.Aula;
import com.auroraacademy.backend.models.Modulo;
import com.auroraacademy.backend.repository.AulaRepository;
import com.auroraacademy.backend.repository.ModuloRepository;

@Service
public class AulaService {
    
    private final AulaRepository aulaRepository;
    private final ModuloRepository moduloRepository;

    public AulaService(AulaRepository aulaRepository, ModuloRepository moduloRepository) {
        this.aulaRepository = aulaRepository;
        this.moduloRepository = moduloRepository;
    }

    public List<Aula> listar() {
        return aulaRepository.findAll();
    }

    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Aula nao encontrado."));
    }

    public List<Aula> listarPorModulo(Long moduloId) {
        return aulaRepository.findByModuloId(moduloId);
    }

    private void validacao(Aula aula) {
        if (aula.getTitulo() == null || aula.getTitulo().isBlank()) {
            throw new IllegalArgumentException("titulo obrigatorio.");
        } else if (aula.getDescricao() == null || aula.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descricao obrigatorio.");
        } else if (aula.getUrlVideo() == null || aula.getUrlVideo().isBlank()) {
            throw new IllegalArgumentException("Url do video obrigatorio.");
        } else if (aula.getOrdem() == null) {
            throw new IllegalArgumentException("Ordem obrigatorio");
        } else if (aula.getModulo() == null || aula.getModulo().getId() == null) {
            throw new IllegalArgumentException("Modulo obrigatorio.");
        }
    }

    public Aula cadastrar(Aula aula) {
        validacao(aula);

        Modulo modulo = moduloRepository.findById(aula.getModulo().getId())
            .orElseThrow(() -> new IllegalArgumentException("Modulo nao encontrado."));

        aula.setModulo(modulo);

        return aulaRepository.save(aula);
    }

    public Aula atualizar(Long id, Aula dadosAtualizados) {
        Aula aula = buscarPorId(id);

        Modulo modulo = moduloRepository.findById(dadosAtualizados.getModulo().getId())
            .orElseThrow(() -> new IllegalArgumentException("Modulo nao encontrado."));

        aula.setTitulo(dadosAtualizados.getTitulo());
        aula.setDescricao(dadosAtualizados.getDescricao());
        aula.setUrlVideo(dadosAtualizados.getUrlVideo());
        aula.setDuracao(dadosAtualizados.getDuracao());
        aula.setOrdem(dadosAtualizados.getOrdem());
        aula.setModulo(modulo);

        validacao(aula);

        return aulaRepository.save(aula);
    }

    public void deletar(Long id) {
        Aula aula = buscarPorId(id);

        aulaRepository.delete(aula);
    }
}
