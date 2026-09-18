# Gerenciador de Tarefas

API REST para gerenciamento de projetos, responsáveis e tarefas, desenvolvida com **Java + Spring Boot + Spring Data JPA + Hibernate + PostgreSQL**.

O projeto foi desenvolvido com foco em demonstrar conceitos de desenvolvimento de APIs REST, persistência de dados, relacionamento entre entidades, regras de negócio, filtros e tratamento de exceções.

---

## 👨‍🎓 Informações do aluno

**Aluno:** José Gustavo Gonçalves da Silva  
**Matrícula:** 47531363

---

## 📌 Sobre o projeto

O sistema permite cadastrar e consultar:

- **Projetos**
- **Responsáveis**
- **Tarefas**

Uma tarefa obrigatoriamente pertence a um projeto e pode possuir um responsável.

Além das operações básicas de cadastro e consulta, a API permite:

- listar tarefas;
- buscar uma tarefa específica;
- atualizar tarefas;
- excluir tarefas;
- filtrar tarefas por status;
- filtrar tarefas por projeto;
- combinar os dois filtros;
- controlar automaticamente a data de conclusão;
- validar a existência de projeto e responsável;
- retornar erros estruturados quando um recurso não é encontrado.

---

# 🛠️ Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java 25 | Linguagem de programação |
| Spring Boot 4.1.1 | Framework principal |
| Spring Web | Construção da API REST |
| Spring Data JPA | Persistência e acesso ao banco |
| Hibernate | ORM |
| PostgreSQL 18 | Banco de dados |
| Maven Wrapper | Gerenciamento e execução do projeto |
| IntelliJ IDEA | IDE utilizada no desenvolvimento |

---

# 🏗️ Arquitetura do projeto

O projeto foi organizado separando responsabilidades entre diferentes camadas:

```text
src/
└── main/
    ├── java/
    │   └── br/com/gustavogoncc/gerenciadortarefas/
    │       ├── controller/
    │       ├── domain/
    │       │   └── exception/
    │       ├── repository/
    │       └── service/
    │
    └── resources/
        └── application.properties
Controller

Responsável por receber as requisições HTTP e expor os endpoints da API.

Exemplos:

ProjetoController
ResponsavelController
TarefaController

Os controllers não concentram as regras de negócio. Eles recebem os dados da requisição e delegam as operações aos services.

Service

Responsável pelas regras de negócio da aplicação.

Exemplos:

ProjetoService
ResponsavelService
TarefaService

No TarefaService, por exemplo, antes de criar uma tarefa o sistema verifica se o projeto informado existe e, quando informado, se o responsável também existe.

Repository

Responsável pela comunicação com o banco de dados utilizando Spring Data JPA.

Exemplos:

ProjetoRepository
ResponsavelRepository
TarefaRepository

Os repositories estendem JpaRepository, recebendo operações como:

save()
findAll()
findById()
delete()

O TarefaRepository também possui consultas derivadas para filtros:

findByStatus(Status status);

findByProjetoId(Long projetoId);

findByStatusAndProjetoId(Status status, Long projetoId);
🗃️ Modelo de dados

O banco possui três tabelas principais:

PROJETO
   │
   │ 1:N
   ▼
TAREFA
   ▲
   │ N:1
   │
RESPONSAVEL
Projeto

Representa um projeto ao qual as tarefas pertencem.

Campos:

Campo	Tipo
id	Long
nome	String
descricao	String
criadoEm	LocalDateTime

O nome é obrigatório e possui limite de 80 caracteres.

Responsável

Representa uma pessoa responsável por uma tarefa.

Campos:

Campo	Tipo
id	Long
nome	String
email	String

Nome e email são obrigatórios.

Tarefa

Representa uma atividade vinculada a um projeto.

Campos:

Campo	Tipo
id	Long
titulo	String
descricao	String
status	Status
prioridade	Prioridade
prazo	LocalDate
criadaEm	LocalDateTime
concluidaEm	LocalDateTime
projeto	Projeto
responsavel	Responsavel
🔗 Relacionamentos JPA

A entidade Tarefa possui dois relacionamentos ManyToOne.

Projeto
@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "projeto_id", nullable = false)
private Projeto projeto;

Isso significa que toda tarefa precisa estar vinculada a um projeto.

Responsável
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "responsavel_id")
private Responsavel responsavel;

O responsável é opcional.

📊 Status das tarefas

O sistema possui quatro status:

public enum Status {
    NOVA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}
🚦 Prioridade das tarefas

Existem três níveis de prioridade:

public enum Prioridade {
    BAIXA,
    MEDIA,
    ALTA
}

Caso uma prioridade não seja informada na criação ou atualização, a aplicação utiliza MEDIA.

⏱️ Regra de conclusão

A entidade Tarefa possui uma regra para controlar concluidaEm.

Quando uma tarefa passa para:

CONCLUIDA

o sistema registra automaticamente a data e hora:

LocalDateTime.now()

em concluidaEm.

Se uma tarefa que estava concluída voltar para outro status, concluidaEm é removida.

Essa regra está implementada no método atualizar() da entidade Tarefa.

🌐 Endpoints da API

A aplicação roda, por padrão, em:

http://localhost:8080
📁 Projetos
Listar projetos
GET /projetos

Exemplo:

Invoke-RestMethod -Uri "http://localhost:8080/projetos" -Method GET
Buscar projeto
GET /projetos/{id}

Exemplo:

Invoke-RestMethod -Uri "http://localhost:8080/projetos/1" -Method GET
Criar projeto
POST /projetos

Body:

{
  "nome": "Projeto Teste API",
  "descricao": "Projeto criado pela API"
}

Exemplo no PowerShell:

$body = @{
    nome = "Projeto Teste API"
    descricao = "Projeto criado pela API"
} | ConvertTo-Json

Invoke-RestMethod `
  -Uri "http://localhost:8080/projetos" `
  -Method POST `
  -ContentType "application/json; charset=utf-8" `
  -Body $body

A criação retorna HTTP 201 Created.

👤 Responsáveis
Listar responsáveis
GET /responsaveis

Exemplo:

Invoke-RestMethod `
  -Uri "http://localhost:8080/responsaveis" `
  -Method GET
Buscar responsável
GET /responsaveis/{id}
Criar responsável
POST /responsaveis

Body:

{
  "nome": "Maria Oliveira",
  "email": "maria@email.com"
}

Exemplo:

$body = @{
    nome = "Maria Oliveira"
    email = "maria@email.com"
} | ConvertTo-Json

Invoke-RestMethod `
  -Uri "http://localhost:8080/responsaveis" `
  -Method POST `
  -ContentType "application/json; charset=utf-8" `
  -Body $body
✅ Tarefas
Listar tarefas
GET /tarefas

Exemplo:

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas" `
  -Method GET
Buscar tarefa
GET /tarefas/{id}

Exemplo:

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas/1" `
  -Method GET
Criar tarefa
POST /tarefas

Body:

{
  "titulo": "Implementar tela de cadastro",
  "descricao": "Criar a tela de cadastro de usuários",
  "prioridade": "ALTA",
  "prazo": "2026-09-30",
  "projetoId": 1,
  "responsavelId": 3
}

O status não precisa ser informado na criação, pois uma nova tarefa começa automaticamente como:

NOVA
Atualizar tarefa
PUT /tarefas/{id}

Exemplo:

$body = @{
    titulo = "Implementar cadastro de usuários"
    descricao = "Finalizar a tela de cadastro de usuários"
    status = "CONCLUIDA"
    prioridade = "ALTA"
    prazo = "2026-10-02"
    projetoId = 1
    responsavelId = 3
} | ConvertTo-Json

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas/4" `
  -Method PUT `
  -ContentType "application/json; charset=utf-8" `
  -Body $body

Quando o status é alterado para CONCLUIDA, o sistema registra automaticamente concluidaEm.

Excluir tarefa
DELETE /tarefas/{id}

Exemplo:

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas/4" `
  -Method DELETE

A API retorna HTTP 204 No Content quando a exclusão é realizada.

🔎 Filtros de tarefas

A API permite filtrar tarefas através de parâmetros de consulta.

Por status
GET /tarefas?status=NOVA

PowerShell:

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas?status=NOVA" `
  -Method GET
Por projeto
GET /tarefas?projetoId=1

PowerShell:

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas?projetoId=1" `
  -Method GET
Por status e projeto
GET /tarefas?status=NOVA&projetoId=1

PowerShell:

Invoke-RestMethod `
  -Uri "http://localhost:8080/tarefas?status=NOVA&projetoId=1" `
  -Method GET
❌ Tratamento de erros

O projeto possui uma exceção específica:

RecursoNaoEncontradoException

Ela é utilizada quando um recurso solicitado não existe.

Exemplo:

GET /tarefas/999

Resposta:

{
  "erro": "Tarefa nao encontrada: 999",
  "momento": "2026-09-18T16:17:31.1807162"
}

Também existem validações para os relacionamentos.

Por exemplo, ao tentar criar uma tarefa utilizando um projeto inexistente:

{
  "projetoId": 999
}

a API retorna:

{
  "erro": "Projeto nao encontrado: 999",
  "momento": "..."
}

O mesmo ocorre quando é informado um responsável inexistente.

🧪 Testes realizados

Durante a validação da aplicação foram testados:

Projetos
Listagem de projetos;
Criação de projeto;
Consulta posterior confirmando a persistência.
Responsáveis
Listagem de responsáveis;
Criação de responsável.
Tarefas
Criação;
Listagem;
Busca por ID;
Filtro por status;
Filtro por projeto;
Filtro combinado;
Atualização;
Alteração para CONCLUIDA;
Registro automático de concluidaEm;
Exclusão.
Tratamento de erros

Também foram testados:

busca de tarefa inexistente;
criação de tarefa com projeto inexistente;
criação de tarefa com responsável inexistente;
exclusão de tarefa inexistente.
🐘 Banco de dados

O projeto utiliza PostgreSQL.

Banco utilizado nos testes:

gerenciador_tarefas

Configuração utilizada durante os testes:

Host: 127.0.0.1
Porta: 5432
Banco: gerenciador_tarefas

As tabelas principais são:

projeto
responsavel
tarefa

O Hibernate/JPA realizou a persistência e criação da estrutura utilizada pela aplicação.

▶️ Como executar o projeto
1. Pré-requisitos

Instale:

Java 25;
PostgreSQL 18;
IntelliJ IDEA ou outra IDE compatível;
Git, caso o projeto seja clonado.

Verifique o Java:

java -version

Verifique o Maven Wrapper:

.\mvnw.cmd -version
2. Configurar o PostgreSQL

Crie o banco:

CREATE DATABASE gerenciador_tarefas;

Depois, configure as credenciais no arquivo:

src/main/resources/application.properties

Exemplo:

spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/gerenciador_tarefas
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

Substitua SUA_SENHA pela senha configurada no PostgreSQL.

3. Executar a aplicação

No terminal, dentro da pasta do projeto:

.\mvnw.cmd spring-boot:run

Quando a aplicação iniciar corretamente, deverá aparecer uma mensagem semelhante a:

Tomcat started on port 8080 (http)

e:

Started GerenciadorTarefasApplication
🧠 Conceitos demonstrados

Este projeto utiliza diversos conceitos importantes de desenvolvimento backend.

API REST

A aplicação disponibiliza recursos através de endpoints HTTP.

HTTP Methods

São utilizados:

GET
POST
PUT
DELETE
HTTP Status Codes

Exemplos utilizados:

201 Created
204 No Content

Além das respostas de erro para recursos inexistentes.

ORM

O Hibernate realiza o mapeamento entre objetos Java e tabelas do PostgreSQL.

JPA

As entidades são mapeadas utilizando anotações como:

@Entity
@Table
@Id
@GeneratedValue
@Column
@ManyToOne
@JoinColumn
@Enumerated
Spring Data JPA

Os repositories utilizam JpaRepository, reduzindo a necessidade de implementar manualmente operações CRUD.

Injeção de dependência

Os controllers e services recebem suas dependências através do construtor.

DTO / Request

Os controllers utilizam record para representar os dados recebidos nas requisições.

Exemplo:

public record ProjetoRequest(
    String nome,
    String descricao
) {}
Enum

Status e Prioridade restringem os valores aceitos pela aplicação.

Regras de negócio

A aplicação possui regras como:

tarefa começa como NOVA;
prioridade padrão é MEDIA;
projeto é obrigatório;
responsável é opcional;
conclusão registra data e hora;
retorno para outro status remove a data de conclusão.
📚 Fluxo de uma requisição

Um exemplo de criação de tarefa:

Cliente
   │
   │ POST /tarefas
   ▼
TarefaController
   │
   │ dados da requisição
   ▼
TarefaService
   │
   ├── busca Projeto
   │
   ├── busca Responsável
   │
   ▼
TarefaRepository
   │
   ▼
Hibernate / JPA
   │
   ▼
PostgreSQL

O fluxo inverso ocorre para retornar a resposta ao cliente.

📂 Principais classes
GerenciadorTarefasApplication
│
├── controller
│   ├── ProjetoController
│   ├── ResponsavelController
│   └── TarefaController
│
├── domain
│   ├── Projeto
│   ├── Responsavel
│   ├── Tarefa
│   ├── Status
│   ├── Prioridade
│   └── exception
│       └── RecursoNaoEncontradoException
│
├── repository
│   ├── ProjetoRepository
│   ├── ResponsavelRepository
│   └── TarefaRepository
│
└── service
    ├── ProjetoService
    ├── ResponsavelService
    └── TarefaService
⚠️ Observação sobre caracteres no terminal

Durante os testes realizados pelo PowerShell/psql, alguns caracteres acentuados apareceram de forma incorreta no terminal, por exemplo:

GestÃ£o
Gustavo GonÃ§alves

Isso está relacionado à codificação da página de código do console do Windows, conforme informado pelo próprio psql durante a execução.

A aplicação e o banco estavam funcionando normalmente; o problema observado estava na exibição dos caracteres no terminal.

✅ Status do projeto

O backend foi executado e validado localmente.

Foram confirmados:

conexão com PostgreSQL;
criação e persistência das entidades;
relacionamentos entre tabelas;
operações CRUD;
filtros;
atualização de tarefas;
controle de conclusão;
exclusão;
tratamento de recursos inexistentes;
validação de projeto e responsável.

O projeto encontra-se funcional para os requisitos implementados.

👨‍💻 Autor

José Gustavo Gonçalves da Silva
Matrícula: 47531363
