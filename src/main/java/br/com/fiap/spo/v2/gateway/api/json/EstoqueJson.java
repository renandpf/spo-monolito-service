package br.com.fiap.spo.v2.gateway.api.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueJson {
	private Long id;
	private Long produtoId;
	private Long quantidade;
	private Long quantidadeReservada;
}