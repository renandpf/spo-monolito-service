package br.com.fiap.spo.pagamento;

import br.com.fiap.spo.model.Pedido;

public interface PagamentoExternoPort {

	String solicitacaoPagamento(Pedido pedido);

}
