LiterAlura

Uma aplicação Java Spring Boot para buscar e gerenciar informações sobre livros da API Gutendex (Project Gutenberg).

Funcionalidades

- ✅ Buscar livro por título
- ✅ Listar todos os livros cadastrados
- ✅ Listar todos os autores cadastrados
- ✅ Listar autores vivos em determinado ano
- ✅ Listar livros por idioma
- ✅ Estatísticas por idioma
- ✅ Top 10 livros mais baixados

Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- PostgreSQL
- Jackson (para manipulação JSON)
- HttpClient (para requisições HTTP)

Pré-requisitos

- Java JDK 17+
- Maven 4+
- PostgreSQL 16+
- IntelliJ IDEA (opcional)
   CREATE DATABASE literalura;
   CREATE USER seu_usuario WITH PASSWORD 'sua_senha';
   GRANT ALL PRIVILEGES ON DATABASE literalura TO seu_usuario;
