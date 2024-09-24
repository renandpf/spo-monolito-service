package br.com.fiap.spo.controller.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueJson {
	private Long id;
	private String nomeProduto;
	private Long quantidade;
	private Long quantidadeReservada;
}
