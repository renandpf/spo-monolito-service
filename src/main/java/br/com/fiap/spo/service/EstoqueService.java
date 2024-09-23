package br.com.fiap.spo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.spo.dao.EstoqueDao;
import br.com.fiap.spo.model.Estoque;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstoqueService {

	private EstoqueDao estoqueDao;
	
	public List<Estoque> listar() {
		return estoqueDao.listar();
	}

	
	
}
