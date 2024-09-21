package br.com.fiap.spo.pagamento;

import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.StatusPagamento;

public interface PagamentoExternoPort {

	String solicitacaoPagamento(Pedido pedido);

	StatusPagamento obterStatus(String pagamentoExternoId);

}
