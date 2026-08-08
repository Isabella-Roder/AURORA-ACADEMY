package com.auroraacademy.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Modulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer ordem;

    @ManyToOne
    private Curso curso;

    public Modulo() {

    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

}
