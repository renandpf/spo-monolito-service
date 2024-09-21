package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class EstoqueNaoEncontradoException extends SystemBaseException {
	private static final long serialVersionUID = 6838778592633130781L;
	
	private final String code = "spo.monolito.estoqueNaoEncontrado";//NOSONAR
	private final String message = "Estoque não encontrado";//NOSONAR
	private final Integer httpStatus = 404;//NOSONAR

}
