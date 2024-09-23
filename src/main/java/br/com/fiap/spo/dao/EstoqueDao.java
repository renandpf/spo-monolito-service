package br.com.fiap.spo.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.dao.repository.EstoqueRepository;
import br.com.fiap.spo.exception.ErroAoAcessarDatabaseException;
import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.exception.StatusPedidoInvalidoException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.model.Pedido;
import br.com.fiap.spo.model.Produto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class EstoqueDao {

	private final EstoqueRepository estoqueRepository;
	
	public void efetuarBaixa(Pedido pedido) {
		try {
			pedido.efetuarBaixaEstoque();
			pedido.getItens().forEach(i -> estoqueRepository.save(i.getProduto().getEstoque()));
			
		} catch (StatusPedidoInvalidoException e) {
			throw e;
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	public void cancelarReserva(Pedido pedido) {
		try {
			pedido.cancelarReservaEstoque();
			pedido.getItens().forEach(i -> estoqueRepository.save(i.getProduto().getEstoque()));
			
		} catch (StatusPedidoInvalidoException e) {
			throw e;
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	public Optional<Estoque> obtemPorId(Long id) {
		try {
			return estoqueRepository.findById(id);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	public long salvar(Estoque estoque) {
		try {
			return estoqueRepository.save(estoque).getProdutoId();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
}
