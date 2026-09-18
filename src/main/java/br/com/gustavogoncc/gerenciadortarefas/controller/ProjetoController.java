package br.com.gustavogoncc.gerenciadortarefas.controller;

import br.com.gustavogoncc.gerenciadortarefas.domain.Projeto;
import br.com.gustavogoncc.gerenciadortarefas.service.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Projeto> criar(@RequestBody ProjetoRequest request) {
        Projeto projeto = service.criar(request.nome(), request.descricao());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(projeto.getId()).toUri();
        return ResponseEntity.created(location).body(projeto);
    }

    @GetMapping
    public List<Projeto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Projeto buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    public record ProjetoRequest(String nome, String descricao) {}
}
