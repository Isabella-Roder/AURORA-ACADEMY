package com.auroraacademy.backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "certificados",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"aluno_id", "curso_id"}
        ),
        @UniqueConstraint(
            columnNames = "codigo_validacao"
        )
    }
)
public class Certificado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    public Curso curso;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(
        name = "codigo_validacao",
        nullable = false,
        unique = true,
        updatable = false
    )
    private String codigoValidacao;

    public Certificado() {

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

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public String getCodigoValidacao() {
        return codigoValidacao;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setCodigoValidacao(String codigoValidacao) {
        this.codigoValidacao = codigoValidacao;
    }
}
