package br.com.fiap.spo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.dao.repository.EstoqueRepository;
import br.com.fiap.spo.exception.ErroAoAcessarDatabaseException;
import br.com.fiap.spo.model.Estoque;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class EstoqueDao {

	private final EstoqueRepository estoqueRepository;
	
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

	public List<Estoque> listar() {
		try {
			return estoqueRepository.findAll();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
}
