package br.com.fiap.spo.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.dao.repository.ItemRepository;
import br.com.fiap.spo.dao.repository.PedidoRepository;
import br.com.fiap.spo.exception.ErroAoAcessarDatabaseException;
import br.com.fiap.spo.exception.StatusPedidoInvalidoException;
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

	public Optional<Pedido> obterPorPagamentoExternoId(String pagamentoExternoId) {
		try {
			return pedidoRepository.findByIdentificadorPagamentoExterno(pagamentoExternoId);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	public void fechar(Pedido pedido) {
		try {
			pedido.fechar();
			pedidoRepository.save(pedido);
			
		} catch (StatusPedidoInvalidoException e) {
			throw e;
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
		
	}

	public void cancelar(Pedido pedido) {
		try {
			pedido.cancelar();
			pedidoRepository.save(pedido);
			
		} catch (StatusPedidoInvalidoException e) {
			throw e;
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	public Long salvar(Pedido pedido) {
		try {
			return pedidoRepository.save(pedido).getId();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
}
