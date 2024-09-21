package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class ErroAoAcessarDatabaseException extends SystemBaseException {
	private static final long serialVersionUID = 6231412904745635500L;
	
	private final String code = "spo.monolito.erroAoAcessarDatabaseException";//NOSONAR
	private final String message = "Erro ao acessar database";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR
}
