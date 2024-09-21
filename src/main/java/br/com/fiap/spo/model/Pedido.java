package br.com.fiap.spo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.spo.exception.StatusPedidoInvalidoException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dataHora;

	private StatusPedido status;
	
	@Setter
	private String identificadorPagamentoExterno;

	@OneToMany(mappedBy = "pedido")
	private List<Item> itens;

	public Pedido() {
		if(dataHora == null) {
			dataHora = LocalDateTime.now();
		}
		if(status == null) {
			status = StatusPedido.ABERTO;
		}
	}

	public Pedido(List<Item> itens) {
		this();
		this.itens = itens;
	}

	@Transient
	public Map<Produto, Long> getQuantidades() {
		final Map<Produto, Long> quantidades = new HashMap<>();

		for (Item item : itens) {
			quantidades.put(item.getProduto(), item.getQuantidade());
		}

		return quantidades;
	}

	public BigDecimal getValorTotal() {
		return itens.stream().map(Item::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add); 
	}

	public void fechar() {
		if(status.equals(StatusPedido.ABERTO)) {
			status = StatusPedido.FECHADO;
		} else {
			log.warn("Status do pedido não permite esta operação. statusAtual={}", status);
			throw new StatusPedidoInvalidoException();
		}
	}
	
	public void cancelar() {
		if(status.equals(StatusPedido.ABERTO)) {
			status = StatusPedido.CANCELADO;
		} else {
			log.warn("Status do pedido não permite esta operação. statusAtual={}", status);
			throw new StatusPedidoInvalidoException();
		}
	}
	
	public void efetuarBaixaEstoque() {
		if(status.equals(StatusPedido.ABERTO)) {
			itens.forEach(i -> {
				Estoque estoque = i.getProduto().getEstoque();
				estoque.efetuarBaixa(i.getQuantidade());
			});
		} else {
			log.warn("Status do pedido não permite esta operação. statusAtual={}", status);
			throw new StatusPedidoInvalidoException();
		}

		
	}

	public void cancelarReservaEstoque() {
		if(status.equals(StatusPedido.ABERTO)) {
			itens.forEach(i -> {
				Estoque estoque = i.getProduto().getEstoque();
				estoque.efetuarCancelamento(i.getQuantidade());
			});
		} else {
			log.warn("Status do pedido não permite esta operação. statusAtual={}", status);
			throw new StatusPedidoInvalidoException();
		}
	}
}
