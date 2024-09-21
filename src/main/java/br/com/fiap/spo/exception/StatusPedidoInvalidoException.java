package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class StatusPedidoInvalidoException extends SystemBaseException {
	private static final long serialVersionUID = 5393715968682693911L;
	
	private final String code = "spo.monolito.statusPedidoInvalido";//NOSONAR
	private final String message = "O status do pedido não permite essa operação";//NOSONAR
	private final Integer httpStatus = 422;//NOSONAR

}
