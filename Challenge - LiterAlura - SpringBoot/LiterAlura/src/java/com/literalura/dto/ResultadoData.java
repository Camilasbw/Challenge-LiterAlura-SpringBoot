package com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoData(
        @JsonAlias("count") Integer count,
        @JsonAlias("results") List<LivroData> resultados
) {}