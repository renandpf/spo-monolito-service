package br.com.fiap.spo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.spo.controller.json.ItemPedidoJson;
import br.com.fiap.spo.model.Item;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.Produto;
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
	@ResponseStatus(HttpStatus.CREATED)
	public Long criar(@RequestBody(required = true) List<ItemPedidoJson> itensPedido) {
		log.info("Start itensPedido={}", itensPedido);
		
		Long pedidoId = pedidoService.criar(mapToPedido(itensPedido));
		
		log.info("End pedidoId={}", pedidoId);
		return pedidoId;
	}
	
	private Pedido mapToPedido(List<ItemPedidoJson> itensPedido) {
		
		List<Item> itens = itensPedido.stream().map(ip -> {
			Produto produto = new Produto(ip.getProdutoId(), null, null, null, null);
			return new Item(null, ip.getQuantidade(), null, produto);
		}).toList();
		
		return new Pedido(itens);
	}
}
