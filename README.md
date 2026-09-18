# 📋 Gerenciador de Tarefas

> API REST para gerenciamento de projetos, responsáveis e tarefas, desenvolvida com Java e Spring Boot.

---

## 👨‍🎓 Informações Acadêmicas

| Informação | Dados |
|---|---|
| **Aluno** | José Gustavo Gonçalves da Silva |
| **Matrícula** | 47531363 |
| **Curso** | Computação |
| **Tecnologia principal** | Java + Spring Boot |

---

## 🚀 Sobre o Projeto

O **Gerenciador de Tarefas** é uma API REST desenvolvida para gerenciar projetos, responsáveis e tarefas.

A aplicação permite:

- Criar e consultar projetos
- Cadastrar responsáveis
- Criar tarefas vinculadas a projetos
- Associar responsáveis às tarefas
- Atualizar tarefas
- Alterar status e prioridade
- Filtrar tarefas
- Excluir tarefas
- Validar recursos inexistentes
- Persistir os dados em PostgreSQL

---

## 🧩 Funcionalidades

### 📁 Projetos

- `POST /projetos` — Criar projeto
- `GET /projetos` — Listar projetos
- `GET /projetos/{id}` — Buscar projeto por ID

### 👤 Responsáveis

- `POST /responsaveis` — Criar responsável
- `GET /responsaveis` — Listar responsáveis
- `GET /responsaveis/{id}` — Buscar responsável por ID

### ✅ Tarefas

- `POST /tarefas` — Criar tarefa
- `GET /tarefas` — Listar tarefas
- `GET /tarefas/{id}` — Buscar tarefa por ID
- `PUT /tarefas/{id}` — Atualizar tarefa
- `DELETE /tarefas/{id}` — Excluir tarefa

---

## 🔎 Filtros de Tarefas

### Por status

```http
GET /tarefas?status=NOVA
```

### Por projeto

```http
GET /tarefas?projetoId=1
```

### Por status e projeto

```http
GET /tarefas?status=NOVA&projetoId=1
```

---

## 🏗️ Arquitetura

O projeto foi organizado seguindo uma separação em camadas:

```text
src/
└── main/
    ├── java/
    │   └── br/com/gustavogoncc/gerenciadortarefas/
    │       ├── controller/
    │       │   ├── ApiExceptionHandler.java
    │       │   ├── ProjetoController.java
    │       │   ├── ResponsavelController.java
    │       │   └── TarefaController.java
    │       ├── domain/
    │       │   ├── exception/
    │       │   │   └── RecursoNaoEncontradoException.java
    │       │   ├── Prioridade.java
    │       │   ├── Projeto.java
    │       │   ├── Responsavel.java
    │       │   ├── Status.java
    │       │   └── Tarefa.java
    │       ├── repository/
    │       │   ├── ProjetoRepository.java
    │       │   ├── ResponsavelRepository.java
    │       │   └── TarefaRepository.java
    │       └── service/
    │           ├── ProjetoService.java
    │           ├── ResponsavelService.java
    │           └── TarefaService.java
    └── resources/
        └── application.properties
```

### Responsabilidade das camadas

| Camada | Responsabilidade |
|---|---|
| **Controller** | Receber requisições HTTP e retornar respostas |
| **Service** | Concentrar regras de negócio |
| **Repository** | Acessar e persistir dados |
| **Domain** | Representar entidades e regras do domínio |
| **Exception** | Tratar erros específicos da aplicação |

---

## 🗄️ Modelo de Dados

O banco de dados utiliza três entidades principais:

```text
PROJETO (1) ──────────── (N) TAREFA (N) ──────────── (1) RESPONSAVEL
   │                         │                              │
   ├── id                    ├── id                         ├── id
   ├── nome                  ├── titulo                     ├── nome
   ├── descricao             ├── descricao                  └── email
   └── criado_em             ├── status
                             ├── prioridade
                             ├── prazo
                             ├── criada_em
                             ├── concluida_em
                             ├── projeto_id
                             └── responsavel_id
```

### Relacionamentos

- Um **Projeto** pode possuir várias tarefas.
- Uma **Tarefa** pertence obrigatoriamente a um projeto.
- Uma **Tarefa** pode possuir um responsável.
- Um **Responsável** pode estar associado a várias tarefas.

---

## 🔄 Status das Tarefas

| Status | Descrição |
|---|---|
| `NOVA` | Tarefa criada e ainda não iniciada |
| `EM_ANDAMENTO` | Tarefa atualmente em execução |
| `CONCLUIDA` | Tarefa finalizada |
| `CANCELADA` | Tarefa cancelada |

---

## ⚡ Prioridades

| Prioridade | Descrição |
|---|---|
| `BAIXA` | Baixa prioridade |
| `MEDIA` | Prioridade intermediária |
| `ALTA` | Alta prioridade |

---

## 🛠️ Tecnologias

| Tecnologia | Utilização |
|---|---|
| ☕ **Java** | Linguagem principal |
| 🌱 **Spring Boot** | Desenvolvimento da API |
| 🌐 **Spring Web** | Endpoints REST |
| 🗃️ **Spring Data JPA** | Persistência |
| 🐘 **PostgreSQL** | Banco de dados |
| 🔗 **Hibernate** | ORM |
| 📦 **Maven** | Gerenciamento de dependências |
| 🧪 **Postman** | Testes da API |
| 💻 **IntelliJ IDEA** | IDE utilizada |

---

## ⚙️ Requisitos

- Java
- Maven
- PostgreSQL
- IntelliJ IDEA ou outra IDE Java
- Postman (opcional)

---

## 🗄️ Configuração do Banco

Crie o banco:

```sql
CREATE DATABASE gerenciador_tarefas;
```

Configure as credenciais em:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_tarefas
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ Não publique senhas reais no GitHub.

---

## ▶️ Executando o Projeto

Clone o repositório:

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta:

```bash
cd gerenciador-tarefas
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

---

## 🧪 Testando a API

A coleção do Postman está em:

```text
postman/Gerenciador-de-Tarefas.postman_collection.json
```

Ela contém requisições para os principais endpoints da aplicação.

---

## 📌 Exemplos de Requisições

### Criar projeto

```http
POST /projetos
Content-Type: application/json
```

```json
{
  "nome": "Sistema de Gestão",
  "descricao": "Desenvolvimento do sistema de gerenciamento de tarefas"
}
```

### Criar responsável

```http
POST /responsaveis
Content-Type: application/json
```

```json
{
  "nome": "Maria Oliveira",
  "email": "maria@email.com"
}
```

### Criar tarefa

```http
POST /tarefas
Content-Type: application/json
```

```json
{
  "titulo": "Implementar tela de cadastro",
  "descricao": "Criar a tela de cadastro de usuários",
  "prioridade": "ALTA",
  "prazo": "2026-09-30",
  "projetoId": 1,
  "responsavelId": 3
}
```

### Atualizar tarefa

```http
PUT /tarefas/4
Content-Type: application/json
```

```json
{
  "titulo": "Implementar cadastro de usuários",
  "descricao": "Finalizar a tela de cadastro de usuários",
  "status": "CONCLUIDA",
  "prioridade": "ALTA",
  "prazo": "2026-10-02",
  "projetoId": 1,
  "responsavelId": 3
}
```

### Excluir tarefa

```http
DELETE /tarefas/4
```

---

## 🚨 Tratamento de Erros

A aplicação possui tratamento centralizado através do:

```text
ApiExceptionHandler
```

Exemplo de recurso inexistente:

```json
{
  "erro": "Tarefa nao encontrada: 999",
  "momento": "2026-09-18T16:17:31.1807162"
}
```

---

## 🧠 Regras de Negócio

- Uma tarefa deve obrigatoriamente estar vinculada a um projeto.
- O responsável de uma tarefa é opcional.
- Projeto inexistente impede a criação da tarefa.
- Responsável inexistente impede a criação da tarefa.
- Tarefas inexistentes não podem ser buscadas, atualizadas ou excluídas.
- O status inicial de uma nova tarefa é `NOVA`.
- A prioridade padrão é `MEDIA` quando não informada.
- Ao alterar uma tarefa para `CONCLUIDA`, `concluidaEm` é preenchido automaticamente.
- Ao retirar uma tarefa do status `CONCLUIDA`, `concluidaEm` é removido.

---

## ✅ Testes Realizados

- [x] Criar projeto
- [x] Listar projetos
- [x] Criar responsável
- [x] Listar responsáveis
- [x] Criar tarefa
- [x] Listar tarefas
- [x] Buscar tarefa por ID
- [x] Filtrar por status
- [x] Filtrar por projeto
- [x] Filtrar por status + projeto
- [x] Atualizar tarefa
- [x] Alterar status para `CONCLUIDA`
- [x] Registrar `concluidaEm`
- [x] Excluir tarefa
- [x] Validar tarefa inexistente
- [x] Validar projeto inexistente
- [x] Validar responsável inexistente
- [x] Confirmar persistência no PostgreSQL

---

## 📂 Estrutura Final do Projeto

```text
gerenciador-tarefas/
├── .mvn/
├── postman/
│   └── Gerenciador-de-Tarefas.postman_collection.json
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## 🎯 Objetivo Acadêmico

O projeto foi desenvolvido com o objetivo de aplicar conceitos de desenvolvimento de APIs REST utilizando Java e Spring Boot.

Principais conceitos praticados:

- Arquitetura em camadas
- API REST
- Injeção de dependências
- Spring Boot
- Spring Data JPA
- Hibernate
- Mapeamento objeto-relacional
- Entidades JPA
- Relacionamentos `@ManyToOne`
- Enums
- Repositórios
- Serviços
- Controllers
- DTOs através de `records`
- Tratamento de exceções
- Persistência em PostgreSQL
- Consultas derivadas do Spring Data
- Testes de endpoints
- Versionamento com Git

---

## 👨‍💻 Autor

**José Gustavo Gonçalves da Silva**

**Matrícula:** 47531363

Projeto desenvolvido para fins acadêmicos.

---

<div align="center">

### 📋 Gerenciador de Tarefas

**Java • Spring Boot • PostgreSQL • JPA • REST API**

</div>
