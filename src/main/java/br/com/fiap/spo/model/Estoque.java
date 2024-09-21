package br.com.fiap.spo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Estoque {
	@Id
	private Long produtoId;

	private Long quantidadeDisponivel;
	private Long quantidadeReservada;
	
    @OneToOne
    @JoinColumn(name = "produtoId", insertable = false, updatable = false)
    private Produto produto;
    
    
    public void adicionarReserva(Long quantidade) {
    	quantidadeReservada += quantidade;
    }
}
