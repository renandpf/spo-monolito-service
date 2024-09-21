package br.com.fiap.spo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.spo.service.PagamentoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")//NOSONAR
@RequestMapping
@RestController
@AllArgsConstructor
public class PagamentoController {

	private PagamentoService pagamentoService;
	
	//TODO: testar esse fluxo inteiro
	
	@PostMapping("pagamentos/checkout/{pagamentoExternoId}")
	public void checkout(@PathVariable final String pagamentoExternoId) {
		log.info("Start pagamentoExternoId={}", pagamentoExternoId);
		pagamentoService.processarPagamento(pagamentoExternoId);
		log.info("End");
	}
	
}
