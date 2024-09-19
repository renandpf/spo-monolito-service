package br.com.fiap.spo.pedido.controller;

import br.com.fiap.spo.pedido.model.Pedido;
import br.com.fiap.spo.pedido.service.PedidoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PedidoController {

	private PedidoService pedidoService;
	
	public Long criar(Pedido pedido) {
		log.info("Start pedido={}", pedido);
		
		Long pedidoId = pedidoService.criar(pedido);
		
		log.info("End pedidoId={}", pedidoId);
		return pedidoId;
	}
	
}
