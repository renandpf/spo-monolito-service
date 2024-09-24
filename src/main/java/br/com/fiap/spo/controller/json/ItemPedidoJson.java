package br.com.fiap.spo.controller.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoJson {
	private Long quantidade;
	private Long produtoId;
}
