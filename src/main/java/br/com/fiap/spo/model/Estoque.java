package br.com.fiap.spo.model;

import br.com.fiap.spo.exception.SemEstoqueDisponivelException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Estoque {
	@Id
	private Long produtoId;

	private Long quantidadeDisponivel;
	private Long quantidadeReservada;
	
    @OneToOne
    @JoinColumn(name = "produtoId", insertable = false, updatable = false)
    private Produto produto;
    
    public Long obterQuantidadeDisponivel() {
    	return quantidadeDisponivel - quantidadeReservada;
    }
    
    public void adicionarReserva(Long quantidade) {
    	if(obterQuantidadeDisponivel() >= quantidade) {
    		quantidadeReservada += quantidade;
    	} else {
    		log.warn("Quantidade solicitada excede disponivel");
    		throw new SemEstoqueDisponivelException();
    	}
    }
    
    public void efetuarBaixa(Long quantidade) {
    	quantidadeDisponivel -= quantidade;
    	quantidadeReservada -= quantidade;
    }
    
    public void efetuarCancelamento(Long quantidade) {
    	quantidadeReservada -= quantidade;
    }

	public void adicionarItens(Long quantidade) {
		quantidadeDisponivel += quantidade;
		
	}
}
