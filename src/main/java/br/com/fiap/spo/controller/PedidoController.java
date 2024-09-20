package br.com.fiap.spo.controller;

import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.service.PedidoService;
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
