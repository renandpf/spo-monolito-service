package br.com.fiap.spo.v2.gateway.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.spo.exception.ErroAoAcessarEstoqueServiceException;
import br.com.fiap.spo.model.Estoque;
import br.com.fiap.spo.model.Produto;
import br.com.fiap.spo.v2.gateway.EstoqueGateway;
import br.com.fiap.spo.v2.gateway.api.json.EstoqueJson;
import br.com.fiap.starter.http.HttpConnectGateway;
import br.com.fiap.starter.http.dto.HttpConnectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("estoqueRestApiGateway")
@RequiredArgsConstructor
public class EstoqueRestApiGateway implements EstoqueGateway {

	private final HttpConnectGateway httpConnectGateway;
	
	private final ObjectMapper objectMapper;
	
	@Value("${estoque.base-url}")
	private String baseUrl;
	
	@Override
	public Optional<Estoque> obtemPorId(Long estoqueId) {
		
		try {
			final String path = "/estoques";//NOSONAR
			
			HttpConnectDto httpDto = HttpConnectDto.builder()
					.url(baseUrl + path)
					.urlParameters(estoqueId+"")
					.build();
			
			
			String responseBodyStr = httpConnectGateway.get(httpDto);
			
			EstoqueJson estoqueJson = objectMapper.readValue(responseBodyStr, EstoqueJson.class);
			
			Estoque estoque = new Estoque(
					estoqueJson.getId(), 
					estoqueJson.getQuantidade(), 
					estoqueJson.getQuantidadeReservada(), 
					new Produto(estoqueJson.getProdutoId(), null, null, null, null));
			
			return Optional.of(estoque);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarEstoqueServiceException();
		}
	}

	@Override
	public long salvar(Estoque estoque) {
		try {
			final String path = "/estoques";//NOSONAR
			
			EstoqueJson estoqueJson = 
					new EstoqueJson(
							null, 
							estoque.getProduto().getId(), 
							estoque.getQuantidadeDisponivel(), 
							estoque.getQuantidadeReservada());
			
			HttpConnectDto httpDto = HttpConnectDto.builder()
					.url(baseUrl + path)
					.requestBody(estoqueJson)
					.build();
			
			
			String responseBodyStr = httpConnectGateway.postWhithRequestBody(httpDto);
			
			return Long.parseLong(responseBodyStr);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarEstoqueServiceException();
		}
	}

	@Override
	public List<Estoque> listar() {
		try {
			final String path = "/estoques";//NOSONAR
			
			HttpConnectDto httpDto = HttpConnectDto.builder()
					.url(baseUrl + path)
					.build();
			
			String responseBodyStr = httpConnectGateway.get(httpDto);
			
			List<EstoqueJson> estoquesJson = objectMapper.readValue(responseBodyStr, new TypeReference<List<EstoqueJson>>(){});
			
			return estoquesJson.stream().map(ej -> new Estoque(
					ej.getId(), 
					ej.getQuantidade(), 
					ej.getQuantidadeReservada(), 
					new Produto(ej.getProdutoId(), null, null, null, null)))
			.toList();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarEstoqueServiceException();
		}
	}

}
