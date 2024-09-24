package br.com.fiap.spo.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.spo.controller.json.ItemPedidoJson;
import br.com.fiap.spo.dao.repository.EstoqueRepository;
import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.exception.SemEstoqueDisponivelException;
import br.com.fiap.spo.model.Estoque;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
class PedidoControllerComponentTest {

	@Autowired
	private PedidoController pedidoController;

	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Test
	void shouldCriarPedidoComSucesso() {
		final Long idProduto = 1L;
		Estoque estoque = estoqueRepository.findAll().stream().filter(p -> p.getProdutoId().equals(idProduto)).findAny().get();
		Long quantidadeReservaEstoque = estoque.getQuantidadeReservada();
		assertEquals(0, quantidadeReservaEstoque);

		
		ItemPedidoJson item = new ItemPedidoJson(3L, 1L);
		Long pedidoId = pedidoController.criar(Arrays.asList(item));
		assertNotNull(pedidoId);
		
		estoque = estoqueRepository.findAll().stream().filter(p -> p.getProdutoId().equals(idProduto)).findAny().get();
		quantidadeReservaEstoque = estoque.getQuantidadeReservada();
		assertEquals(3, quantidadeReservaEstoque);
	}
	
	@Test
	void shouldCriarPedidoSemEstoque() {
		ItemPedidoJson item = new ItemPedidoJson(50L, 1L);
		List<ItemPedidoJson> itens = Arrays.asList(item);
		assertThrows(SemEstoqueDisponivelException.class, ()-> pedidoController.criar(itens));
	}
	
	@Test
	void shouldCriarEstoqueNaoEncontrado() {
		ItemPedidoJson item = new ItemPedidoJson(1L, 50L);
		List<ItemPedidoJson> itens = Arrays.asList(item);
		assertThrows(EstoqueNaoEncontradoException.class, ()-> pedidoController.criar(itens));
	}
}
