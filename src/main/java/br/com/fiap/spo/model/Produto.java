package br.com.fiap.spo.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
	@Id
	private Long id;

	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<Item> itens;
	
	@OneToOne(mappedBy = "produto")
	private Estoque estoque;
	
	private String nome;
	
	private BigDecimal valor;
}
