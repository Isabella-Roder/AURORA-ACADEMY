package com.auroraacademy.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auroraacademy.backend.models.ProgressoAula;
import com.auroraacademy.backend.service.ProgressoAulaService;

@RestController
@RequestMapping("/progressos")
public class ProgressoAulaController {
    
    private final ProgressoAulaService progressoAulaService;

    public ProgressoAulaController(ProgressoAulaService progressoAulaService) {
        this.progressoAulaService = progressoAulaService;
    }

    @PostMapping("/alunos/{alunoId}/aulas/{aulaId}/concluir")
    public ProgressoAula marcarConcluida(@PathVariable Long alunoId, @PathVariable Long aulaId) {
        return progressoAulaService.marcarConcluida(alunoId, aulaId);
    }

    @GetMapping("/alunos/{alunoId}/cursos/{cursoId}")
    public List<ProgressoAula> listarPorCurso(@PathVariable Long alunoId, @PathVariable Long cursoId) {
        return progressoAulaService.listarPorCurso(alunoId, cursoId);
    }

    @DeleteMapping("/alunos/{alunoId}/aulas/{aulaId}/concluir")
    public ResponseEntity<Void> desmarcarConcluida(@PathVariable Long alunoId, @PathVariable Long aulaId) {
        progressoAulaService.desmarcarConcluida(alunoId, aulaId);

        return ResponseEntity.noContent().build();
    }

}
