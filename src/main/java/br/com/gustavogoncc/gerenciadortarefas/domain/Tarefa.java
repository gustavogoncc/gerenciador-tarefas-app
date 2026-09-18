package br.com.gustavogoncc.gerenciadortarefas.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Prioridade prioridade;

    private LocalDate prazo;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    private LocalDateTime concluidaEm;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    protected Tarefa() {
    }

    public Tarefa(String titulo, String descricao, Prioridade prioridade, LocalDate prazo,
                  Projeto projeto, Responsavel responsavel) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = Status.NOVA;
        this.prioridade = prioridade == null ? Prioridade.MEDIA : prioridade;
        this.prazo = prazo;
        this.criadaEm = LocalDateTime.now();
        this.projeto = projeto;
        this.responsavel = responsavel;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Status getStatus() { return status; }
    public Prioridade getPrioridade() { return prioridade; }
    public LocalDate getPrazo() { return prazo; }
    public LocalDateTime getCriadaEm() { return criadaEm; }
    public LocalDateTime getConcluidaEm() { return concluidaEm; }
    public Projeto getProjeto() { return projeto; }
    public Responsavel getResponsavel() { return responsavel; }

    public void atualizar(String titulo, String descricao, Status novoStatus, Prioridade prioridade,
                          LocalDate prazo, Projeto projeto, Responsavel responsavel) {
        Status statusAnterior = this.status;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = novoStatus == null ? Status.NOVA : novoStatus;
        this.prioridade = prioridade == null ? Prioridade.MEDIA : prioridade;
        this.prazo = prazo;
        this.projeto = projeto;
        this.responsavel = responsavel;

        if (this.status == Status.CONCLUIDA && statusAnterior != Status.CONCLUIDA) {
            this.concluidaEm = LocalDateTime.now();
        } else if (this.status != Status.CONCLUIDA) {
            this.concluidaEm = null;
        }
    }
}
