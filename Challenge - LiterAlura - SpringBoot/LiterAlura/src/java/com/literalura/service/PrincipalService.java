package com.literalura.service;

import com.literalura.model.Livro;
import com.literalura.model.Autor;
import com.literalura.repository.LivroRepository;
import com.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class PrincipalService {
    private final Scanner scanner;
    private final GutendexService gutendexService;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public PrincipalService(GutendexService gutendexService,
                            LivroRepository livroRepository,
                            AutorRepository autorRepository) {
        this.scanner = new Scanner(System.in);
        this.gutendexService = gutendexService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibirMenu() {
        var opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== LITERALURA ===");
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar todos os livros");
            System.out.println("3 - Listar todos os autores");
            System.out.println("4 - Listar autores vivos em um ano");
            System.out.println("5 - Listar livros por idioma");
            System.out.println("6 - Estatísticas por idioma");
            System.out.println("7 - Top 10 livros mais baixados");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        buscarLivroPorTitulo();
                        break;
                    case 2:
                        listarTodosLivros();
                        break;
                    case 3:
                        listarTodosAutores();
                        break;
                    case 4:
                        listarAutoresVivosNoAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 6:
                        exibirEstatisticasIdioma();
                        break;
                    case 7:
                        listarTop10Downloads();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        Optional<Livro> livro = gutendexService.buscarLivroPorTitulo(titulo);

        if (livro.isPresent()) {
            System.out.println("Livro encontrado e salvo: " + livro.get());
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private void listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado!");
        } else {
            System.out.println("\n=== LIVROS CADASTRADOS ===");
            livros.forEach(System.out::println);
        }
    }

    private void listarTodosAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado!");
        } else {
            System.out.println("\n=== AUTORES CADASTRADOS ===");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosNoAno() {
        System.out.print("Digite o ano: ");
        try {
            Integer ano = Integer.parseInt(scanner.nextLine());
            List<Autor> autores = autorRepository.findAutoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado no ano " + ano);
            } else {
                System.out.println("\n=== AUTORES VIVOS EM " + ano + " ===");
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um ano válido!");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Digite o idioma (ex: en, es, fr, pt): ");
        String idioma = scanner.nextLine();

        List<Livro> livros = livroRepository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma " + idioma);
        } else {
            System.out.println("\n=== LIVROS EM " + idioma.toUpperCase() + " ===");
            livros.forEach(System.out::println);
        }
    }

    private void exibirEstatisticasIdioma() {
        System.out.print("Digite o idioma para estatísticas (ex: en, es, fr, pt): ");
        String idioma = scanner.nextLine();

        Long quantidade = livroRepository.countByIdioma(idioma);
        System.out.println("Quantidade de livros em " + idioma + ": " + quantidade);
    }

    private void listarTop10Downloads() {
        List<Livro> top10 = livroRepository.findTop10ByOrderByNumeroDownloadsDesc();

        if (top10.isEmpty()) {
            System.out.println("Nenhum livro encontrado!");
        } else {
            System.out.println("\n=== TOP 10 LIVROS MAIS BAIXADOS ===");
            top10.forEach(livro -> System.out.println(
                    livro.getTitulo() + " - " + livro.getNumeroDownloads() + " downloads"
            ));
        }
    }
}