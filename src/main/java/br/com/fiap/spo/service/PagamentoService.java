package br.com.fiap.spo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.spo.dao.PedidoDao;
import br.com.fiap.spo.exception.PedidoNaoEncontradoException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.StatusPagamento;
import br.com.fiap.spo.pagamento.PagamentoExternoPort;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagamentoService {
	
	@Autowired//NOSONAR
	private PagamentoExternoPort pagamentoExternoPort;
	
	@Autowired//NOSONAR
	@Qualifier("estoqueRestApiGateway")
	private EstoqueGateway estoqueGateway;

	@Autowired//NOSONAR
	private PedidoDao pedidoDao;

	@Transactional
	public void processarPagamento(String pagamentoExternoId) {
		
		var statusPagamento = pagamentoExternoPort.obterStatus(pagamentoExternoId);
		
		var pedido = obterPedido(pagamentoExternoId);
		
		processaEstoque(statusPagamento, pedido);
		processaPedido(statusPagamento, pedido);
	}

	private Pedido obterPedido(String pagamentoExternoId) {
		var pedidoOp = pedidoDao.obterPorPagamentoExternoId(pagamentoExternoId);
		Pedido pedido = pedidoOp.orElseThrow(() -> {
			log.warn("Pedido não encontrado com identificador de pagamento externo. pagamentoExternoId={}", pagamentoExternoId);
			return new PedidoNaoEncontradoException();
		});
		
		pedido.getItens().forEach(i -> {
			Estoque estoque = estoqueGateway.obtemPorId(i.getProduto().getId()).get();
			i.getProduto().setEstoque(estoque);
		});
		
		return pedido;
	}

	private void processaEstoque(StatusPagamento statusPagamento, Pedido pedido) {
		if(statusPagamento.equals(StatusPagamento.SUCESSO)) {
			pedido.efetuarBaixaEstoque();
		} else {
			pedido.cancelarReservaEstoque();
		}
		pedido.getItens().forEach(i -> estoqueGateway.salvar(i.getProduto().getEstoque()));
	}
	
	private void processaPedido(StatusPagamento statusPagamento, Pedido pedido) {
		if(statusPagamento.equals(StatusPagamento.SUCESSO)) {
			pedido.fechar();
		} else {
			pedido.cancelar();
		}
		pedidoDao.salvar(pedido);
	}
}
