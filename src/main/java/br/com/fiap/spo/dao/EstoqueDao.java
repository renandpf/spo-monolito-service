package br.com.fiap.spo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.spo.dao.repository.EstoqueRepository;
import br.com.fiap.spo.exception.ErroAoAcessarDatabaseException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
@AllArgsConstructor
@Deprecated(forRemoval = true)
public class EstoqueDao implements EstoqueGateway {

	private final EstoqueRepository estoqueRepository;
	
	@Override
	public Optional<Estoque> obtemPorId(Long id) {
		log.warn("Esta operação ser decontinuada");
		//throw new RuntimeException();
		try {
			return estoqueRepository.findById(id);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	@Override
	public long salvar(Estoque estoque) {
		log.warn("Esta operação ser decontinuada");
		//throw new RuntimeException();
		try {
			return estoqueRepository.save(estoque).getId();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}

	@Override
	public List<Estoque> listar() {
		log.warn("Esta operação ser decontinuada");
		//throw new RuntimeException();
		try {
			return estoqueRepository.findAll();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarDatabaseException();
		}
	}
}
