package br.com.fiap.spo.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.spo.controller.json.ItemPedidoJson;
import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.exception.ProdutoNaoEncontradoException;
import br.com.fiap.spo.exception.SemEstoqueDisponivelException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
class PedidoControllerComponentTest {

	@Autowired
	private PedidoController pedidoController;
	
	@Test
	void shouldCriarPedidoComSucesso() {
		
		ItemPedidoJson item = new ItemPedidoJson(3L, 1L);
		Long criar = pedidoController.criar(Arrays.asList(item));
		assertNotNull(criar);
		
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
