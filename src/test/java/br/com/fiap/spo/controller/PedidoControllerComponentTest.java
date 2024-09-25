package br.com.fiap.spo.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.spo.controller.json.ItemPedidoJson;
import br.com.fiap.spo.dao.repository.PedidoRepository;
import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.exception.SemEstoqueDisponivelException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
class PedidoControllerComponentTest {

	@Autowired
	private PedidoController pedidoController;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@MockBean
	@Qualifier("estoqueRestApiGateway")
	private EstoqueGateway estoqueGateway;
	
	@Test
	void shouldCriarPedidoComSucesso() {
		final Long idProduto = 1L;
		
		final int quantidadePedidosExistentes = pedidoRepository.findAll().size();
		
		Estoque estoque = new Estoque(idProduto, 10L, 0L, null);
		doReturn(Optional.of(estoque)).when(estoqueGateway).obtemPorId(idProduto);
		
		ItemPedidoJson item = new ItemPedidoJson(3L, idProduto);
		Long pedidoId = pedidoController.criar(Arrays.asList(item));
		assertNotNull(pedidoId);
		
		final int quantidadePedidosAtualizas = pedidoRepository.findAll().size();

		final int quantidadePedidosEsperados = quantidadePedidosExistentes + 1;
		
		assertEquals(quantidadePedidosEsperados, quantidadePedidosAtualizas);
		
		assertEquals(3L, estoque.getQuantidadeReservada());
		
		verify(estoqueGateway).salvar(estoque);
	}
	
	@Test
	void shouldCriarPedidoSemEstoque() {
		final Long idProduto = 1L;
		Estoque estoque = new Estoque(idProduto, 49L, 0L, null);
		doReturn(Optional.of(estoque)).when(estoqueGateway).obtemPorId(idProduto);

		
		ItemPedidoJson item = new ItemPedidoJson(50L, idProduto);
		List<ItemPedidoJson> itens = Arrays.asList(item);
		assertThrows(SemEstoqueDisponivelException.class, ()-> pedidoController.criar(itens));
	}
	
	@Test
	void shouldCriarEstoqueNaoEncontrado() {
		final Long idProduto = 1L;
		doReturn(Optional.empty()).when(estoqueGateway).obtemPorId(idProduto);
		
		ItemPedidoJson item = new ItemPedidoJson(1L, 50L);
		List<ItemPedidoJson> itens = Arrays.asList(item);
		assertThrows(EstoqueNaoEncontradoException.class, ()-> pedidoController.criar(itens));
	}
}
