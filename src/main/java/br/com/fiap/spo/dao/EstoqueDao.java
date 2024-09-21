package br.com.fiap.spo.dao;

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
	
	public Long obtemQuantidadeDisponivel(Produto produto) {
		try {
			Estoque estoque = getEstoqueById(produto);
			
			//Esta regra poderia estar dentro do model. Proposital ficou aqui pra simular um "legado".
			return estoque.getQuantidadeDisponivel() - estoque.getQuantidadeReservada();
			
		} catch (EstoqueNaoEncontradoException e) {
			log.warn("Estoque não encontrado. id={}", produto.getId());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
		
	}

	public void reserva(Produto produto, Long quantidadeSolicitada) {
		try {
			Estoque estoque = getEstoqueById(produto);
			estoque.adicionarReserva(quantidadeSolicitada);
			
			estoqueRepository.save(estoque);
			
		} catch (EstoqueNaoEncontradoException e) {
			log.warn("Estoque não encontrado. id={}", produto.getId());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
	
	private Estoque getEstoqueById(Produto produto) {
		return estoqueRepository.findById(produto.getId())
		.orElseThrow(EstoqueNaoEncontradoException::new);
	}

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
}
