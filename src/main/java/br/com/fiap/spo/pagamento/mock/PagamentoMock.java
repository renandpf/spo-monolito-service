package br.com.fiap.spo.pagamento.mock;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.pagamento.PagamentoExternoPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PagamentoMock implements PagamentoExternoPort {

	@Override
	public String solicitacaoPagamento(Pedido pedido) {
		log.warn("## PAGAMENTO MOCK ##");
		return UUID.randomUUID().toString();
	}

}
