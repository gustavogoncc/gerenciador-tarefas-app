package br.com.gustavogoncc.gerenciadortarefas.controller;

import br.com.gustavogoncc.gerenciadortarefas.domain.Responsavel;
import br.com.gustavogoncc.gerenciadortarefas.service.ResponsavelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {

    private final ResponsavelService service;

    public ResponsavelController(ResponsavelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Responsavel> criar(@RequestBody ResponsavelRequest request) {
        Responsavel responsavel = service.criar(request.nome(), request.email());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(responsavel.getId()).toUri();
        return ResponseEntity.created(location).body(responsavel);
    }

    @GetMapping
    public List<Responsavel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Responsavel buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    public record ResponsavelRequest(String nome, String email) {}
}
