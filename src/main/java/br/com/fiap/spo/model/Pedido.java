package br.com.fiap.spo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dataHora;

	@Setter
	private String identificadorPagamentoExterno;

	@OneToMany(mappedBy = "pedido")
	private List<Item> itens;

	public Pedido() {
		if(dataHora == null) {
			dataHora = LocalDateTime.now();
		}
	}

	public Pedido(List<Item> itens) {
		this();
		this.itens = itens;
	}

	@Transient
	public Map<Produto, Long> getQuantidades() {
		final Map<Produto, Long> quantidades = new HashMap<>();

		for (Item item : itens) {
			quantidades.put(item.getProduto(), item.getQuantidade());
		}

		return quantidades;
	}

	public BigDecimal getValorTotal() {
		return itens.stream().map(Item::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add); 
	}

}
