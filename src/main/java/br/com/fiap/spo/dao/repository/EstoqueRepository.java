package br.com.fiap.spo.dao.repository;

import java.util.List;
import java.util.Optional;

import br.com.fiap.spo.model.Estoque;

@Deprecated(forRemoval = true)
public interface EstoqueRepository /*extends JpaRepository<Estoque, Long>*/ {

	Optional<Estoque> findById(Long id);

	Estoque save(Estoque estoque);

	List<Estoque> findAll();

}
