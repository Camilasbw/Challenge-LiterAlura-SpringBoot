package com.literalura.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "livros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Construtores
    public Livro() {}

    public Livro(String titulo, String idioma, Integer numeroDownloads, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.autor = autor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonAlias("title")
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    @JsonAlias("languages")
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    @JsonAlias("download_count")
    public Integer getNumeroDownloads() { return numeroDownloads; }
    public void setNumeroDownloads(Integer numeroDownloads) { this.numeroDownloads = numeroDownloads; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDownloads=" + numeroDownloads +
                ", autor=" + (autor != null ? autor.getNome() : "Desconhecido") +
                '}';
    }
}