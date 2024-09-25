package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class ErroAoAcessarEstoqueServiceException extends SystemBaseException {
	private static final long serialVersionUID = 7144075035859962957L;

	private final String code = "spo.monolito.erroAoAcessarEstoqueService";//NOSONAR
	private final String message = "Erro ao acessar sistema de estoque";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR
}
