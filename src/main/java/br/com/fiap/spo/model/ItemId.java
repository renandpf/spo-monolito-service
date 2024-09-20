package br.com.fiap.spo.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ItemId implements Serializable {
	private static final long serialVersionUID = -498294596441288882L;
	
	private Long idPedido;
	private Long idProduto;
}
