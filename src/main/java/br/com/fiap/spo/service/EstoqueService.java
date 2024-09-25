package br.com.fiap.spo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Deprecated(forRemoval = true)
public class EstoqueService {

	@Autowired//NOSONAR
	@Qualifier("estoqueRestApiGateway")
	private EstoqueGateway estoqueGateway;
	
	@Deprecated
	public List<Estoque> listar() {
		return estoqueGateway.listar();
	}

	@Deprecated
	public void adicionarItens(Long id, Long quantidade) {
		Estoque estoque = obterEstoque(id);
		
		estoque.adicionarItens(quantidade);
		
		estoqueGateway.salvar(estoque);
	}

	private Estoque obterEstoque(Long id) {
		return estoqueGateway.obtemPorId(id).orElseThrow(() -> {
			log.warn("Estoque não encontrado. id={}", id);
			throw new EstoqueNaoEncontradoException();
		});
	}
}
