package br.com.fiap.spo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.service.PedidoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")//NOSONAR
@RequestMapping
@RestController
@AllArgsConstructor
public class PedidoController {

	private PedidoService pedidoService;
	
	@PostMapping("pedidos")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Long criar(@RequestBody(required = true) Pedido pedido) throws Exception {
		//FIXME: alterar requestBody para uma classe representativa Json
		log.info("Start pedido={}", pedido);
		
		Long pedidoId = pedidoService.criar(pedido);
		
		log.info("End pedidoId={}", pedidoId);
		return pedidoId;
	}
	
}
