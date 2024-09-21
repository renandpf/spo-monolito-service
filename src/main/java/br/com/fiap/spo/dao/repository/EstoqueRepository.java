package br.com.fiap.spo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.spo.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}
