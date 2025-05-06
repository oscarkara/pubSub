# 📰 Sistema de Publicação de Notícias com Assinaturas e Entregas Assíncronas

Este projeto é uma aplicação backend robusta construída com **Spring Boot**, que permite a **publicação de notícias** em **tópicos específicos**, com um sistema de **inscrição de usuários**,**entrega assíncrona via RabbitMQ** e **Envio Automático de Notícias recebidas do RabbitMQ para WebSocket**. O objetivo é garantir que cada usuário receba notificações em tempo real apenas dos tópicos em que está inscrito.

---

## 🚧 Funcionalidades

- ✅ Registro e autenticação de usuários com JWT
- ✅ Criação, listagem e exclusão de **tópicos**
- ✅ Publicação de **notícias** em tópicos
- ✅ **Inscrição/remoção** de usuários em tópicos
- ✅ **Entrega de mensagens via RabbitMQ** para cada usuário inscrito
- ✅ Integração com **WebSocket** (para entrega em tempo real)
- ✅ Segurança com controle de acesso via Spring Security

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

- **Java 21**
- **Spring Boot 3**
  - Spring Web
  - Spring Security + JWT
  - Spring Data JPA
- **RabbitMQ** (mensageria assíncrona com tópicos/filas por usuário)
- **PostgreSQL** (persistência dos dados)
- **Docker** (RabbitMQ containerizado)
- **Swagger/OpenAPI** (documentação de endpoints)
- **WebSocket** (opcional para push realtime frontend)

---

## 🧠 Principais Desafios Técnicos

### 🔐 Autenticação e JWT com UUID
- O sistema foi desenhado para trabalhar com UUIDs como identificadores primários dos usuários.
- Foi necessário customizar o processo de autenticação do JWT para **extração e validação do UUID**, usando um `UserDetails` próprio (`UserLoginDetails`), e adaptando todo o fluxo de `SecurityContext`.

### 🔄 Integração com RabbitMQ
- Foi utilizado **exchanges nomeados com o UUID do tópico**, e **filas dinâmicas** por usuário/tópico.
- O sistema garante que **somente usuários inscritos** recebam as mensagens, respeitando isolamento total.
- Cada publicação em um tópico gera um `convertAndSend()` específico para o exchange do tópico.
- A exclusão de um tópico exige a **remoção de todas as filas associadas**, gerenciadas com `AmqpAdmin`.

### ⚙️ Segurança com Spring Security
- O acesso aos endpoints é restrito por autenticação JWT.
- A extração do usuário da sessão é feita com `@AuthenticationPrincipal` diretamente no controller.
- Endpoints públicos e protegidos foram devidamente separados com `HttpSecurity`.

---

## 🚀 Como Rodar a Aplicação

Siga os passos abaixo para configurar e rodar localmente o projeto **PubSub**:

### 📦 Pré-requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL rodando localmente na porta `5432`
- RabbitMQ rodando localmente na porta `5672` e `15672`
- Docker + Docker Compose, se quiser rodar RabbitMQ por container

---
### 🛠️ Configuração

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/oscarkara/pubsub.git
   cd pubsub
   ```

2. **Crie o arquivo `application.properties`** na pasta `src/main/resources/` com base no exemplo `application-example.properties`, localizado no mesmo diretório.
</br>
3. **Configure as variáveis** no novo `application.properties` (ou defina via variáveis de ambiente), **principalmente** as seguintes:

   ```properties
   jwt.secret=umSegredoSuperForte123!
   spring.datasource.password=suaSenhaPostgres
   ```

4. **Configure e execute o Docker Compose** utilizando o arquivo `docker-compose.yml` na raiz do repositório:

   ```bash
   docker-compose up
   ```

5. **Rode a aplicação com:**

   ```bash
   ./mvnw spring-boot:run
   ```

6. **Acesse a documentação Swagger** em:  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📂 Estrutura de Pastas (simplificada)

```
src/
├── config/               # Configurações da aplicação, JWT, RabbitMQ, Websocket
├── controller/           # Endpoints REST
├── dto/                  # Objetos de transporte de dados
├── model/                # Entidades JPA
├── repository/           # Interfaces JPA
├── security/             # JWT, filtros, detalhes de autenticação
└── service/              # Regras de negócio

```
