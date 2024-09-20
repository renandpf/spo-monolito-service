package br.com.fiap.spo.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Transient;
import lombok.Setter;


public class Pedido {
	@Setter
	private String identificadorPagamentoExterno;
	private List<Item> itens;

	@Transient
	public Map<Produto, Long> getQuantidades() {
		final Map<Produto, Long> quantidades = new HashMap<Produto, Long>();
		
		for (Item item : itens) {
			quantidades.put(item.getProduto(), item.getQuantidade());
		}
		
		return quantidades;
	}
	
	public BigDecimal getValorTotal() {
		return itens.stream().map(Item::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add); 
}
}
