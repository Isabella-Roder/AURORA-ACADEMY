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

import com.auroraacademy.backend.models.Modulo;
import com.auroraacademy.backend.service.ModuloService;

@RestController
@RequestMapping("/modulos")
public class ModuloController {
    
    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @GetMapping
    public List<Modulo> listar() {
        return moduloService.listar();
    }

    @GetMapping("/{id}")
    public Modulo buscarPorId(@PathVariable Long id) {
        return moduloService.buscarPorId(id);
    }

    @GetMapping("/curso/{cursoId}")
    public List<Modulo> listarPorCurso(@PathVariable Long cursoId) {
        return moduloService.listarPorCurso(cursoId);
    }

    @PostMapping
    public Modulo cadastrar(@RequestBody Modulo modulo) {
        return moduloService.cadastrar(modulo);
    }

    @PutMapping("/{id}")
    public Modulo atualizar(@PathVariable Long id, @RequestBody Modulo modulo) {
        return moduloService.atualizar(id, modulo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        moduloService.deletar(id);
    }
}
