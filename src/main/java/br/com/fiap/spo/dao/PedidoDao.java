package br.com.fiap.spo.dao;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.dao.repository.ItemRepository;
import br.com.fiap.spo.dao.repository.PedidoRepository;
import br.com.fiap.spo.exception.ErroAoAcessarDatabaseException;
import br.com.fiap.spo.model.ItemId;
import br.com.fiap.spo.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class PedidoDao {

	private final PedidoRepository pedidoRepository;
	private final ItemRepository itemRepository;
	
	public Long criar(Pedido pedido) {
		try {
			final Long pedidoId = pedidoRepository.save(pedido).getId();
			pedido.getItens().forEach(i -> {
				i.setId(new ItemId(pedidoId, i.getProduto().getId()));
				itemRepository.save(i);
			});
			
			return pedidoId;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
}
