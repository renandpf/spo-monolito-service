package br.com.fiap.spo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.spo.controller.json.EstoqueJson;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.service.EstoqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")//NOSONAR
@RequestMapping
@RestController
@AllArgsConstructor
public class EstoqueController {

	private EstoqueService estoqueService;
	
	@GetMapping("estoques")
	public List<EstoqueJson> listar(){
		log.info("Start");
		
		List<Estoque> estoques =  estoqueService.listar();
		List<EstoqueJson> estoquesJson = estoques.stream()
			.map(e -> 
				new EstoqueJson(
						e.getProdutoId(), 
						e.getProduto().getNome(), 
						e.getQuantidadeDisponivel(), 
						e.getQuantidadeReservada())).toList();
		
		
		log.info("End estoquesJson={}", estoquesJson);
		return estoquesJson;
	}
	
}
