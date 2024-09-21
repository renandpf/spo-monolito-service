package br.com.fiap.spo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.spo.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
