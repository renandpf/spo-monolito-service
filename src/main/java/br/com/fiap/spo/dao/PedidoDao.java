package br.com.fiap.spo.dao;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.dao.repository.PedidoRepository;
import br.com.fiap.spo.exception.ErroAoAcessarDatabaseException;
import br.com.fiap.spo.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class PedidoDao {

	private final PedidoRepository pedidoRepository;
	
	public Long criar(Pedido pedido) {
		try {
			
			return pedidoRepository.save(pedido).getId();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
}
