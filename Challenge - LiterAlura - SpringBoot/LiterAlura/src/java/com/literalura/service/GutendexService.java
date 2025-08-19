package com.literalura.service;

import com.literalura.dto.ResultadoData;
import com.literalura.dto.LivroData;
import com.literalura.model.Livro;
import com.literalura.model.Autor;
import com.literalura.repository.LivroRepository;
import com.literalura.repository.AutorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {
    private static final String GUTENDEX_API_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public GutendexService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Optional<Livro> buscarLivroPorTitulo(String titulo) {
        try {
            String url = GUTENDEX_API_URL + "?search=" + titulo.replace(" ", "%20");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ResultadoData resultado = objectMapper.readValue(response.body(), ResultadoData.class);

                if (resultado.resultados() != null && !resultado.resultados().isEmpty()) {
                    LivroData livroData = resultado.resultados().get(0);
                    return salvarLivro(livroData);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<Livro> salvarLivro(LivroData livroData) {
        try {
            // Processar autor (pegar o primeiro da lista)
            Autor autor = null;
            if (livroData.autores() != null && !livroData.autores().isEmpty()) {
                var autorData = livroData.autores().get(0);
                autor = autorRepository.findByNome(autorData.nome())
                        .orElseGet(() -> {
                            Autor novoAutor = new Autor(
                                    autorData.nome(),
                                    autorData.anoNascimento(),
                                    autorData.anoFalecimento()
                            );
                            return autorRepository.save(novoAutor);
                        });
            }

            // Processar idioma (pegar o primeiro da lista)
            String idioma = livroData.idiomas() != null && !livroData.idiomas().isEmpty()
                    ? livroData.idiomas().get(0)
                    : "Desconhecido";

            Livro livro = new Livro(
                    livroData.titulo(),
                    idioma,
                    livroData.numeroDownloads(),
                    autor
            );

            return Optional.of(livroRepository.save(livro));
        } catch (Exception e) {
            System.err.println("Erro ao salvar livro: " + e.getMessage());
            return Optional.empty();
        }
    }
}
