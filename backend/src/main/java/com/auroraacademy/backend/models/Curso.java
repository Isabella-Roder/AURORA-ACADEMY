package com.auroraacademy.backend.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.auroraacademy.backend.enums.NivelCurso;
import com.auroraacademy.backend.enums.StatusCurso;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String categoria;
    
    @Enumerated(EnumType.STRING)
    private NivelCurso nivelCurso;

    private LocalTime cargaHoraria;
    private Double preco;
    private String imgUrl;
    
    @Enumerated(EnumType.STRING)
    private StatusCurso statusCurso;

    @ManyToOne
    private Usuario professor;
    private LocalDate dataCriacao;

    public Curso() {

    }

    public Curso(
        String titulo,
        String descricao,
        String categoria,
        NivelCurso nivelCurso,
        LocalTime cargaHoraria,
        Double preco,
        String imgUrl,
        StatusCurso statusCurso,
        Usuario professor,
        LocalDate dataCriacao
    ) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.nivelCurso = nivelCurso;
        this.cargaHoraria = cargaHoraria;
        this.preco = preco;
        this.imgUrl = imgUrl;
        this.statusCurso = statusCurso;
        this.professor = professor;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public NivelCurso getNivelCurso() {
        return nivelCurso;
    }

    public LocalTime getCargaHoraria() {
        return cargaHoraria;
    }

    public Double getPreco() {
        return preco;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public StatusCurso getStatusCurso() {
        return statusCurso;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setNivelCurso(NivelCurso nivelCurso) {
        this.nivelCurso = nivelCurso;
    }

    public void setCargaHoraria(LocalTime cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setStatusCurso(StatusCurso statusCurso) {
        this.statusCurso = statusCurso;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}
