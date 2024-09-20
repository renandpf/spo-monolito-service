package br.com.fiap.spo.service;

import java.util.Map;

import br.com.fiap.spo.dao.EstoqueDao;
import br.com.fiap.spo.dao.PedidoDao;
import br.com.fiap.spo.exception.QuantidadeSolicitadaExcedeDisponivelExcetption;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.Produto;
import br.com.fiap.spo.pagamento.PagamentoExternoPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PedidoService {

	private EstoqueDao estoqueDao;
	private PagamentoExternoPort pagamentoExterno;
	private PedidoDao pedidoDao;
	

	@Transactional
	public Long criar(Pedido pedido) {
		
		Map<Produto, Long> produtosQuantidade = pedido.getQuantidades();
		
		produtosQuantidade.entrySet().stream().forEach(es -> {
			Produto produto = es.getKey();
			Long quantidadeSolicitada = es.getValue();
			
			verificaQuantidadeDisponivel(produto, quantidadeSolicitada);
			
			estoqueDao.reserva(produto, quantidadeSolicitada);
		});
		
		final String identificadorPagamentoExterno = pagamentoExterno.solicitacaoPagamento(pedido);
		pedido.setIdentificadorPagamentoExterno(identificadorPagamentoExterno);
		
		return pedidoDao.criar(pedido);
	}


	private void verificaQuantidadeDisponivel(Produto produto, Long quantidadeSolicitada) {
		Long quantidadeDisponivel = estoqueDao.obtemQuantidadeDisponivel(produto);
		if(quantidadeSolicitada > quantidadeDisponivel) {
			log.warn("Quantidade solicitada excede o valor disponível: quantidadeSolicitada={}, quantidadeDisponivel={}", 
					quantidadeSolicitada, quantidadeDisponivel);
			throw new QuantidadeSolicitadaExcedeDisponivelExcetption();
		}
	}

}
