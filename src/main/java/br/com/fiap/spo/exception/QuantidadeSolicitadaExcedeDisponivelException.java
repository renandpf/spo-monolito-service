package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class QuantidadeSolicitadaExcedeDisponivelException extends SystemBaseException {
	private static final long serialVersionUID = -4222378474173384839L;
	
	private final String code = "spo.monolito.quantidadeExcedeDisponivel";//NOSONAR
	private final String message = "Quantidade excede o disponível no estoque";//NOSONAR
	private final Integer httpStatus = 422;//NOSONAR
}
