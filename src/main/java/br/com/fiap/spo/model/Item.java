package br.com.fiap.spo.model;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	@Setter
	@EmbeddedId
	private ItemId id;
	
	private Long quantidade;

	@Setter
    @ManyToOne
    @JoinColumn(name = "pedidoId", insertable = false, updatable = false)
	private Pedido pedido;
    
	@Setter
    @ManyToOne
    @JoinColumn(name = "produtoId", insertable = false, updatable = false)
    private Produto produto;
	
	public BigDecimal getPreco() {
		return produto.getValor().multiply(new BigDecimal(quantidade));
	}
}
