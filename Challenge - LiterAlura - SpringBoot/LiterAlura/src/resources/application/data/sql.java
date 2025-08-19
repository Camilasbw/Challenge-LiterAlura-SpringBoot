-- Criar o banco de dados
CREATE DATABASE literalura;

-- Criar um usuário específico (opcional mas recomendado)
CREATE USER literalura_user WITH PASSWORD 'literalura_password';

        -- Conceder privilégios ao usuário
GRANT ALL PRIVILEGES ON DATABASE literalura TO literalura_user;
