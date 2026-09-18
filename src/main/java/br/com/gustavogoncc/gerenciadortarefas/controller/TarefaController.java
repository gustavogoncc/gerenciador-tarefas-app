package br.com.gustavogoncc.gerenciadortarefas.controller;

import br.com.gustavogoncc.gerenciadortarefas.domain.*;
import br.com.gustavogoncc.gerenciadortarefas.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody TarefaRequest request) {
        Tarefa tarefa = service.criar(
                request.titulo(), request.descricao(), request.prioridade(), request.prazo(),
                request.projetoId(), request.responsavelId()
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(tarefa.getId()).toUri();
        return ResponseEntity.created(location).body(tarefa);
    }

    @GetMapping
    public List<Tarefa> listar(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long projetoId) {
        return service.listar(status, projetoId);
    }

    @GetMapping("/{id}")
    public Tarefa buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Tarefa atualizar(@PathVariable Long id, @RequestBody TarefaRequest request) {
        return service.atualizar(
                id, request.titulo(), request.descricao(), request.status(), request.prioridade(),
                request.prazo(), request.projetoId(), request.responsavelId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    public record TarefaRequest(
            String titulo,
            String descricao,
            Status status,
            Prioridade prioridade,
            LocalDate prazo,
            Long projetoId,
            Long responsavelId
    ) {}
}
