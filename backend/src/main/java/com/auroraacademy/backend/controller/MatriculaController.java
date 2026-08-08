package com.auroraacademy.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auroraacademy.backend.enums.StatusMatricula;
import com.auroraacademy.backend.models.Matricula;
import com.auroraacademy.backend.service.MatriculaService;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public List<Matricula> listar() {
        return matriculaService.listar();
    }

    @GetMapping("/{id}")
    public Matricula buscarPorId(@PathVariable Long id) {
        return matriculaService.buscarPorId(id);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<Matricula> listarPorAluno(@PathVariable Long alunoId) {
        return matriculaService.listarPorAluno(alunoId);
    }

    @GetMapping("/curso/{cursoId}")
    public List<Matricula> listarPorCurso(@PathVariable Long cursoId) {
        return matriculaService.listarPorCurso(cursoId);
    }

    @GetMapping("/data-matricula/{dataMatricula}")
    public List<Matricula> listarPorDataMatricula(@PathVariable LocalDateTime dataMatricula) {
        return matriculaService.listarPorDataMatricula(dataMatricula);
    }

    @GetMapping("/status-matricula/{statusMatricula}")
    public List<Matricula> listarPorStatus(@PathVariable StatusMatricula statusMatricula) {
        return matriculaService.listarPorStatus(statusMatricula);
    }

    @PostMapping
    public Matricula matricular(@RequestBody Matricula matricula) {
        return matriculaService.matricular(matricula);
    }

    @PostMapping("/{id}")
    public Matricula cancelar(@PathVariable Long id) {
        return matriculaService.cancelar(id);
    }
}
