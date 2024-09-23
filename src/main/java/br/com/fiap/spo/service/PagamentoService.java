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

@Slf4j
@Service
@AllArgsConstructor
public class PagamentoService {
	
	private final PagamentoExternoPort pagamentoExternoPort;
	private EstoqueDao estoqueDao;
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
		return pedidoOp.orElseThrow(() -> {
			log.warn("Pedido não encontrado com identificador de pagamento externo. pagamentoExternoId={}", pagamentoExternoId);
			return new PedidoNaoEncontradoException();
		});
	}

	private void processaEstoque(StatusPagamento statusPagamento, Pedido pedido) {
		if(statusPagamento.equals(StatusPagamento.SUCESSO)) {
			pedido.efetuarBaixaEstoque();
		} else {
			pedido.cancelarReservaEstoque();
		}
		pedido.getItens().forEach(i -> estoqueDao.salvar(i.getProduto().getEstoque()));
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
