package com.literalura.repository;

import com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findAll();

    @Query("SELECT l FROM Livro l WHERE l.idioma = :idioma")
    List<Livro> findByIdioma(@Param("idioma") String idioma);

    @Query("SELECT COUNT(l) FROM Livro l WHERE l.idioma = :idioma")
    Long countByIdioma(@Param("idioma") String idioma);

    @Query("SELECT l FROM Livro l ORDER BY l.numeroDownloads DESC LIMIT 10")
    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}