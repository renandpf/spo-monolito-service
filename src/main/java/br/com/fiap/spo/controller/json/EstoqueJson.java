package br.com.fiap.spo.controller.json;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstoqueJson {
	private Long id;
	private String nomeProduto;
	private Long quantidade;
	private Long quantidadeReservada;
}
