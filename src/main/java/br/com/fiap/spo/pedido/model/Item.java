package br.com.fiap.spo.pedido.model;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class Item {
	private Produto produto;
	private Long quantidade;
	
	public BigDecimal getPreco() {
		return produto.getValor().multiply(new BigDecimal(quantidade));
	}
}
