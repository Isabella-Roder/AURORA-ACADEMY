package com.auroraacademy.backend.models;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Aula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String urlVideo;
    private LocalTime duracao;
    private Integer ordem;

    @ManyToOne
    private Modulo modulo;

    public Aula() {

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

    public String getUrlVideo() {
        return urlVideo;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }
}
