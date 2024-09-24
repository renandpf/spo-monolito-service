package br.com.fiap.spo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.spo.dao.EstoqueDao;
import br.com.fiap.spo.exception.EstoqueNaoEncontradoException;
import br.com.fiap.spo.model.Estoque;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EstoqueService {

	private EstoqueDao estoqueDao;
	
	public List<Estoque> listar() {
		return estoqueDao.listar();
	}

	public void adicionarItens(Long id, Long quantidade) {
		Estoque estoque = obterEstoque(id);
		
		estoque.adicionarItens(quantidade);
		
		estoqueDao.salvar(estoque);
	}

	private Estoque obterEstoque(Long id) {
		return estoqueDao.obtemPorId(id).orElseThrow(() -> {
			log.warn("Estoque não encontrado. id={}", id);
			throw new EstoqueNaoEncontradoException();
		});
	}
}
