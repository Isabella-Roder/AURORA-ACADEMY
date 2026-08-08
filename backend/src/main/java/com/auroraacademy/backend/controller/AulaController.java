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

import com.auroraacademy.backend.models.Aula;
import com.auroraacademy.backend.service.AulaService;

@RestController
@RequestMapping("/aulas")
public class AulaController {
    
    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @GetMapping
    public List<Aula> listar() {
        return aulaService.listar();
    }

    @GetMapping("/{id}")
    public Aula buscarPorId(@PathVariable Long id) {
        return aulaService.buscarPorId(id);
    }

    @GetMapping("/modulos/{moduloId}")
    public List<Aula> listarPorModulo(@PathVariable Long moduloId) {
        return aulaService.listarPorModulo(moduloId);
    }

    @PostMapping
    public Aula cadastrar(@RequestBody Aula aula) {
        return aulaService.cadastrar(aula);
    }

    @PutMapping("/{id}")
    public Aula atualizar(@PathVariable Long id, @RequestBody Aula aula) {
        return aulaService.atualizar(id, aula);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        aulaService.deletar(id);
    }
}
