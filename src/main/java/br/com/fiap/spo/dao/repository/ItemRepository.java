package br.com.fiap.spo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.spo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
