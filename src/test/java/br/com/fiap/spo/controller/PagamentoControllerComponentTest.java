package br.com.fiap.spo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.spo.controller.json.ItemPedidoJson;
import br.com.fiap.spo.dao.repository.PedidoRepository;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.StatusPedido;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
class PagamentoControllerComponentTest {

	@Autowired
	private PagamentoController pagamentoController;
	
	@Autowired
	private PedidoController pedidoController;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@MockBean
	@Qualifier("estoqueRestApiGateway")
	private EstoqueGateway estoqueGateway;
	
	@Test
	void shouldCriarPedidoComSucesso() {
		final Long idProduto = 2L;
		final Long quantidade = 3L;
		
		Estoque estoque = new Estoque(idProduto, 10L, 0L, null);
		doReturn(Optional.of(estoque)).when(estoqueGateway).obtemPorId(idProduto);
		
		ItemPedidoJson item = new ItemPedidoJson(quantidade, idProduto);
		Long pedidoId = pedidoController.criar(Arrays.asList(item));
		
		Pedido pedido = pedidoRepository.findById(pedidoId).get();
		assertEquals(StatusPedido.ABERTO, pedido.getStatus());

		pagamentoController.checkout(pedido.getIdentificadorPagamentoExterno());
		
		pedido = pedidoRepository.findById(pedidoId).get();
		assertEquals(StatusPedido.FECHADO, pedido.getStatus());
		
		verify(estoqueGateway, times(2)).salvar(estoque);
	}
}
