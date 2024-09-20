package br.com.fiap.spo.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Item {
	@EmbeddedId
	private ItemId id;
	
	private Long quantidade;

    @ManyToOne
    @JoinColumn(name = "idCarrinho", insertable = false, updatable = false)
	private Pedido pedido;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idProduto", insertable = false, updatable = false)
    private Produto produto;
	
	public BigDecimal getPreco() {
		return produto.getValor().multiply(new BigDecimal(quantidade));
	}
}
