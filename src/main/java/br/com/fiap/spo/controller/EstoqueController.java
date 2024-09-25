package br.com.fiap.spo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@Deprecated(forRemoval = true)
public class EstoqueController {

	private EstoqueService estoqueService;
	
	@Deprecated
	@GetMapping("estoques")
	public List<EstoqueJson> listar(){
		log.info("Start");
		
		List<Estoque> estoques =  estoqueService.listar();
		List<EstoqueJson> estoquesJson = estoques.stream()
			.map(e -> 
				new EstoqueJson(
						e.getId(), 
						e.getProduto().getNome(), 
						e.getQuantidadeDisponivel(), 
						e.getQuantidadeReservada())).toList();
		
		
		log.info("End estoquesJson={}", estoquesJson);
		return estoquesJson;
	}
	
	@Deprecated
	@PatchMapping("estoques/{id}")
	public void adicionar(@PathVariable Long id, @RequestBody(required = true) EstoqueJson estoqueJson){
		log.info("Start");

		estoqueService.adicionarItens(id, estoqueJson.getQuantidade());
		
		log.info("End");
	}
}
