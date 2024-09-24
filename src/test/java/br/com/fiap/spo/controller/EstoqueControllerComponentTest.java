package br.com.fiap.spo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.fiap.spo.controller.json.EstoqueJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
class EstoqueControllerComponentTest {

	@Autowired
	private EstoqueController estoqueController;
	
	@Test
	void shouldListarEstoques() {
		
		List<EstoqueJson> estoques = estoqueController.listar();
		
		assertEquals(1L, estoques.get(0).getId());
		assertEquals("Caneta", estoques.get(0).getNomeProduto());
		
		assertEquals(2L, estoques.get(1).getId());
		assertEquals("Teclado", estoques.get(1).getNomeProduto());
		
		assertEquals(3L, estoques.get(2).getId());
		assertEquals("Camera", estoques.get(2).getNomeProduto());
		
	}
	
	@Test
	void shouldAdicionarEstoque() {
		
		Long estoqueId = 1L;
		Long quantidadeAAdicionar = 15L;
		
		EstoqueJson estoque = estoqueController.listar().stream().filter(e -> e.getId().equals(estoqueId)).findAny().get();
		
		final Long quantidadeAntes = estoque.getQuantidade();
		
		estoqueController.adicionar(1L, new EstoqueJson(null, null, quantidadeAAdicionar, null));

		final Long quantidadeEsperada = quantidadeAAdicionar + quantidadeAntes;
		
		estoque = estoqueController.listar().stream().filter(e -> e.getId().equals(estoqueId)).findAny().get();
		
		assertEquals(quantidadeEsperada, estoque.getQuantidade());
	}
	
}
