package com.literalura.repository;

import com.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);

    List<Autor> findAll();

    @Query("SELECT a FROM Autor a WHERE (:ano BETWEEN a.anoNascimento AND a.anoFalecimento) OR " +
            "(a.anoNascimento <= :ano AND a.anoFalecimento IS NULL)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);
}