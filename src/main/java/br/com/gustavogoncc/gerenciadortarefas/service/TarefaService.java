package br.com.gustavogoncc.gerenciadortarefas.service;

import br.com.gustavogoncc.gerenciadortarefas.domain.*;
import br.com.gustavogoncc.gerenciadortarefas.domain.exception.RecursoNaoEncontradoException;
import br.com.gustavogoncc.gerenciadortarefas.repository.ProjetoRepository;
import br.com.gustavogoncc.gerenciadortarefas.repository.ResponsavelRepository;
import br.com.gustavogoncc.gerenciadortarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final ResponsavelRepository responsavelRepository;

    public TarefaService(TarefaRepository tarefaRepository,
                         ProjetoRepository projetoRepository,
                         ResponsavelRepository responsavelRepository) {
        this.tarefaRepository = tarefaRepository;
        this.projetoRepository = projetoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    public Tarefa criar(String titulo, String descricao, Prioridade prioridade, LocalDate prazo,
                        Long projetoId, Long responsavelId) {
        Projeto projeto = buscarProjeto(projetoId);
        Responsavel responsavel = buscarResponsavel(responsavelId);

        return tarefaRepository.save(
                new Tarefa(titulo, descricao, prioridade, prazo, projeto, responsavel)
        );
    }

    public List<Tarefa> listar(Status status, Long projetoId) {
        if (status != null && projetoId != null) {
            return tarefaRepository.findByStatusAndProjetoId(status, projetoId);
        }
        if (status != null) {
            return tarefaRepository.findByStatus(status);
        }
        if (projetoId != null) {
            return tarefaRepository.findByProjetoId(projetoId);
        }
        return tarefaRepository.findAll();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa nao encontrada: " + id));
    }

    public Tarefa atualizar(Long id, String titulo, String descricao, Status status,
                            Prioridade prioridade, LocalDate prazo, Long projetoId,
                            Long responsavelId) {
        Tarefa tarefa = buscarPorId(id);
        Projeto projeto = buscarProjeto(projetoId);
        Responsavel responsavel = buscarResponsavel(responsavelId);

        tarefa.atualizar(titulo, descricao, status, prioridade, prazo, projeto, responsavel);
        return tarefaRepository.save(tarefa);
    }

    public void excluir(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
    }

    private Projeto buscarProjeto(Long id) {
        if (id == null) {
            throw new RecursoNaoEncontradoException("Projeto nao informado");
        }
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto nao encontrado: " + id));
    }

    private Responsavel buscarResponsavel(Long id) {
        if (id == null) {
            return null;
        }
        return responsavelRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Responsavel nao encontrado: " + id));
    }
}
