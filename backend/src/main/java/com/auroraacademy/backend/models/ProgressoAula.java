package com.auroraacademy.backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"aluno_id", "aula_id"}
    )
)
public class ProgressoAula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario aluno;

    @ManyToOne
    private Aula aula;

    private Boolean concluida;
    private LocalDateTime dataConclusao;

    public ProgressoAula() {
    }

    public Long getId() {
        return id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public Aula getAula() {
        return aula;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

}
