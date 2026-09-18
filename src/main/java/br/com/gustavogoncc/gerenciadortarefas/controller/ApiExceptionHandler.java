package br.com.gustavogoncc.gerenciadortarefas.controller;

import br.com.gustavogoncc.gerenciadortarefas.domain.exception.RecursoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResponse tratarRecursoNaoEncontrado(RecursoNaoEncontradoException exception) {
        return new ErroResponse(exception.getMessage(), LocalDateTime.now());
    }

    public record ErroResponse(String erro, LocalDateTime momento) {}
}
