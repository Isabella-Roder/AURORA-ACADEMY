package com.auroraacademy.backend.models;

import java.time.LocalDateTime;

import com.auroraacademy.backend.enums.StatusMatricula;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Matricula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario aluno;

    @ManyToOne
    private Curso curso;

    private LocalDateTime dataMatricula;

    @Enumerated(EnumType.STRING)
    private StatusMatricula statusMatricula;

    public Matricula() {

    }

    public Matricula(
        Usuario aluno,
        Curso curso,
        LocalDateTime dataMatricula,
        StatusMatricula statusMatricula
    ) {
        this.aluno = aluno;
        this.curso = curso;
        this.dataMatricula = dataMatricula;
        this.statusMatricula = statusMatricula;
    }

    public Long getId() {
        return id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public StatusMatricula getStatusMatricula() {
        return statusMatricula;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public void setStatusMatricula(StatusMatricula statusMatricula) {
        this.statusMatricula = statusMatricula;
    }
}
