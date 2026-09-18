package br.com.gustavogoncc.gerenciadortarefas.service;

import br.com.gustavogoncc.gerenciadortarefas.domain.Responsavel;
import br.com.gustavogoncc.gerenciadortarefas.domain.exception.RecursoNaoEncontradoException;
import br.com.gustavogoncc.gerenciadortarefas.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {

    private final ResponsavelRepository repository;

    public ResponsavelService(ResponsavelRepository repository) {
        this.repository = repository;
    }

    public Responsavel criar(String nome, String email) {
        return repository.save(new Responsavel(nome, email));
    }

    public List<Responsavel> listar() {
        return repository.findAll();
    }

    public Responsavel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Responsavel nao encontrado: " + id));
    }
}
