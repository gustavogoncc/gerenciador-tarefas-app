package br.com.gustavogoncc.gerenciadortarefas.service;

import br.com.gustavogoncc.gerenciadortarefas.domain.Projeto;
import br.com.gustavogoncc.gerenciadortarefas.domain.exception.RecursoNaoEncontradoException;
import br.com.gustavogoncc.gerenciadortarefas.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository repository;

    public ProjetoService(ProjetoRepository repository) {
        this.repository = repository;
    }

    public Projeto criar(String nome, String descricao) {
        return repository.save(new Projeto(nome, descricao));
    }

    public List<Projeto> listar() {
        return repository.findAll();
    }

    public Projeto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto nao encontrado: " + id));
    }
}
