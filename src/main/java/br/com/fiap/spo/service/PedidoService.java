package br.com.fiap.spo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.spo.dao.PedidoDao;
import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.Produto;
import br.com.fiap.spo.pagamento.PagamentoExternoPort;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoService {

	@Autowired//NOSONAR
	@Qualifier("estoqueRestApiGateway")
	private EstoqueGateway estoqueGateway;
	
	@Autowired//NOSONAR
	private PagamentoExternoPort pagamentoExterno;

	@Autowired//NOSONAR
	private PedidoDao pedidoDao;
	

	@Transactional
	public Long criar(Pedido pedido) {
		
		reservarEstoque(pedido);
		
		solictaPagamentoExterno(pedido);
		
		return pedidoDao.criar(pedido);
	}


	private void solictaPagamentoExterno(Pedido pedido) {
		final String identificadorPagamentoExterno = pagamentoExterno.solicitacaoPagamento(pedido);
		pedido.setIdentificadorPagamentoExterno(identificadorPagamentoExterno);
	}


	private void reservarEstoque(Pedido pedido) {
		Map<Produto, Long> produtosQuantidade = pedido.getQuantidades();
		
		produtosQuantidade.entrySet().stream().forEach(es -> {
			Produto produto = es.getKey();
			Long quantidadeSolicitada = es.getValue();
			
			var estoqueOp = estoqueGateway.obtemPorId(produto.getId());
			Estoque estoque = estoqueOp.orElseThrow(() -> {
				log.warn("Estoque não encontrado");
				return new EstoqueNaoEncontradoException();
			});
			estoque.adicionarReserva(quantidadeSolicitada);
			
			estoqueGateway.salvar(estoque);
		});
	}
}
