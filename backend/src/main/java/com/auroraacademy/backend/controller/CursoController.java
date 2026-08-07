package com.auroraacademy.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auroraacademy.backend.enums.NivelCurso;
import com.auroraacademy.backend.enums.StatusCurso;
import com.auroraacademy.backend.models.Curso;
import com.auroraacademy.backend.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @GetMapping("/nivel/{nivel}")
    public List<Curso> listarPorNivel(@PathVariable NivelCurso nivel) {
        return cursoService.listarPorNivel(nivel);
    }

    @GetMapping("/status/{status}")
    public List<Curso> listarPorStatus(@PathVariable StatusCurso status) {
        return cursoService.listarPorStatus(status);
    }

    @GetMapping("/professor/{professorId}")
    public List<Curso> listarPorProfessorId(@PathVariable Long professorId) {
        return cursoService.listarPorProfessorId(professorId);
    }

    @PostMapping
    public Curso cadastrar(@RequestBody Curso curso) {
        return cursoService.cadastrar(curso);
    }

    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return cursoService.atualizar(id, curso);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cursoService.deletar(id);
    }

}
