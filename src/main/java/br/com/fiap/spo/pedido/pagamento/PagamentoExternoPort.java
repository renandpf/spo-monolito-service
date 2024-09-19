package br.com.fiap.spo.pedido.pagamento;

import br.com.fiap.spo.pedido.model.Pedido;

public interface PagamentoExternoPort {

	String solicitacaoPagamento(Pedido pedido);

}
