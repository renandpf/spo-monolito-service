package br.com.fiap.spo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Pedido {
	
	@Id
	private Long id;
	
	private LocalDateTime dataHora;
	
	@Setter
	private String identificadorPagamentoExterno;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
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
