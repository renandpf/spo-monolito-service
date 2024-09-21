package br.com.fiap.spo.service;

import org.springframework.stereotype.Service;

import br.com.fiap.spo.dao.EstoqueDao;
import br.com.fiap.spo.dao.PedidoDao;
import br.com.fiap.spo.exception.PedidoNaoEncontradoException;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.StatusPagamento;
import br.com.fiap.spo.pagamento.PagamentoExternoPort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PagamentoService {
	
	private final PagamentoExternoPort pagamentoExternoPort;
	private EstoqueDao estoqueDao;
	private PedidoDao pedidoDao;

	@Transactional
	public void processarPagamento(String pagamentoExternoId) {
		
		var statusPagamento = pagamentoExternoPort.obterStatus(pagamentoExternoId);
		
		Pedido pedido = obterPedido(pagamentoExternoId);
		
		processaEstoque(statusPagamento, pedido);
		processaPedido(statusPagamento, pedido);
	}

	private Pedido obterPedido(String pagamentoExternoId) {
		var pedidoOp = pedidoDao.obterPorPagamentoExternoId(pagamentoExternoId);
		return pedidoOp.orElseThrow(() -> {
			log.warn("Pedido não encontrado com identificador de pagamento externo. pagamentoExternoId={}", pagamentoExternoId);
			return new PedidoNaoEncontradoException();
		});
	}

	private void processaEstoque(StatusPagamento statusPagamento, Pedido pedido) {
		if(statusPagamento.equals(StatusPagamento.SUCESSO)) {
			estoqueDao.efetuarBaixa(pedido);
		} else {
			estoqueDao.cancelarReserva(pedido);
		}
	}
	
	private void processaPedido(StatusPagamento statusPagamento, Pedido pedido) {
		if(statusPagamento.equals(StatusPagamento.SUCESSO)) {
			pedidoDao.fechar(pedido);
		} else {
			pedidoDao.cancelar(pedido);
		}
	}

}
