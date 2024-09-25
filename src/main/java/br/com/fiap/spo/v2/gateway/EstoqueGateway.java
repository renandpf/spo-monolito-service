package br.com.fiap.spo.v2.gateway;

import java.util.List;
import java.util.Optional;

import br.com.fiap.spo.model.Estoque;

public interface EstoqueGateway {
	Optional<Estoque> obtemPorId(Long estoqueId);
	public long salvar(Estoque estoque);
	public List<Estoque> listar();
}
