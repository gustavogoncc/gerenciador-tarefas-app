package br.com.gustavogoncc.gerenciadortarefas.repository;

import br.com.gustavogoncc.gerenciadortarefas.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
