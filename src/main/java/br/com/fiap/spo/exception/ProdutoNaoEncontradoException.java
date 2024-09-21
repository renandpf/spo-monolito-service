package br.com.fiap.spo.exception;

import lombok.Getter;

@Getter
public class ProdutoNaoEncontradoException extends SystemBaseException {
	private static final long serialVersionUID = -1162682536132417288L;
	
	private final String code = "spo.monolito.produtoNaoEncontrado";//NOSONAR
	private final String message = "Produto não encontrado";//NOSONAR
	private final Integer httpStatus = 404;//NOSONAR

}
