package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class PedidoNaoEncontradoException extends SystemBaseException {
	private static final long serialVersionUID = -6550463122061288092L;
	
	private final String code = "spo.monolito.pedidoNaoEncontrado";//NOSONAR
	private final String message = "Pedido não foi encontrado";//NOSONAR
	private final Integer httpStatus = 404;//NOSONAR{

}
