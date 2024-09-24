package br.com.fiap.spo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.spo.controller.json.ItemPedidoJson;
import br.com.fiap.spo.dao.repository.EstoqueRepository;
import br.com.fiap.spo.dao.repository.PedidoRepository;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.StatusPedido;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
class PagamentoControllerComponentTest {

	@Autowired
	private PagamentoController pagamentoController;
	
	@Autowired
	private PedidoController pedidoController;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Test
	void shouldCriarPedidoComSucesso() {
		final Long idProduto = 2L;
		final Long quantidade = 3L;
		Estoque estoque = estoqueRepository.findAll().stream().filter(p -> p.getProdutoId().equals(idProduto)).findAny().get();
		final Long quantidadeDisponivelEstoque = estoque.getQuantidadeDisponivel();
		
		ItemPedidoJson item = new ItemPedidoJson(quantidade, idProduto);
		Long pedidoId = pedidoController.criar(Arrays.asList(item));
		
		Pedido pedido = pedidoRepository.findById(pedidoId).get();
		assertEquals(StatusPedido.ABERTO, pedido.getStatus());

		pagamentoController.checkout(pedido.getIdentificadorPagamentoExterno());
		
		pedido = pedidoRepository.findById(pedidoId).get();
		assertEquals(StatusPedido.FECHADO, pedido.getStatus());
		
		estoque = estoqueRepository.findAll().stream().filter(p -> p.getProdutoId().equals(idProduto)).findAny().get();
		
		assertEquals((quantidadeDisponivelEstoque-quantidade), estoque.getQuantidadeDisponivel());
		assertEquals(0, estoque.getQuantidadeReservada());
	}
}
