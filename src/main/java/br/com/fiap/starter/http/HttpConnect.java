package br.com.fiap.starter.http;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import br.com.fiap.starter.http.dto.HttpConnectDto;
import br.com.fiap.starter.http.exception.HttpConnectorException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HttpConnect implements HttpConnectGateway {

	private static final String APPLICATION_JSON_KEY = "application/json";
	private static final String CONTENT_TYPE_KEY = "Content-Type";
	private static final String AUTHORIZATION_KEY = "Authorization";


	public String postWithRequestBodyMultipart(HttpConnectDto dto) {
		try {
			String url = dto.getUrl();
			
			if (dto.getUrlParameters() != null) {
				url = url.concat(dto.getUrlParameters());
			} 
			
			MultiValueMap<String, String> formData = dto.getFormData();
			
			final WebClient webClient = WebClient.create();
			
			return webClient.post()
					.uri(url)
					.body(Mono.just(formData), MultiValueMap.class)
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
		} catch (Exception e) {
			throw processException(e);
		}

	}

	public String postWhithRequestBody(HttpConnectDto dto) {
		try {
			String url = dto.getUrl();
			
			if (dto.getUrlParameters() != null) {
				url = url.concat("?").concat(dto.getUrlParameters());
			} 
			
			String token = dto.getHeaders() == null ? "" : dto.getHeaders().get(AUTHORIZATION_KEY);
			
			final WebClient webClient = WebClient.create();
			
			return	webClient.post()
					.uri(url)
					.body(Mono.just(dto.getRequestBody()), dto.getRequestBody().getClass())
					.header(CONTENT_TYPE_KEY, APPLICATION_JSON_KEY)
					.header(AUTHORIZATION_KEY, token)
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
		} catch (Exception e) {
			throw processException(e);
		}

	}
	
	public String get(HttpConnectDto dto) {
		try {
			String url = dto.getUrl();
			if(dto.getQueryParams() != null) {
				url = url.concat(dto.getQueryParamUrl());
			}
			
			final WebClient webClient = WebClient.create();
			
			String token = dto.getHeaders() == null ? "" : dto.getHeaders().get(AUTHORIZATION_KEY);
			
			ResponseSpec responseSpec = 
					webClient.get()
					.uri(url)
					.header(CONTENT_TYPE_KEY, APPLICATION_JSON_KEY)
					.header(AUTHORIZATION_KEY, token)
					.retrieve();
			
			return responseSpec.bodyToMono(String.class).block();
			
		} catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	public String patch(HttpConnectDto dto) {
		try {
			String url = dto.getUrl();
			
			if (dto.getUrlParameters() != null) {
				url = url.concat("?").concat(dto.getUrlParameters());
			} 
			
			final WebClient webClient = WebClient.create();
			
			return webClient.patch()
					.uri(url)
					.body(Mono.just(dto.getRequestBody()), dto.getRequestBody().getClass())
					.header(CONTENT_TYPE_KEY, APPLICATION_JSON_KEY)
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
		} catch (Exception e) {
			throw processException(e);
		}
	}

	
	private HttpConnectorException processException(Exception e) {
		int statusCode = 500;
		String message = e.getMessage();
		
		if(e instanceof WebClientResponseException ex) {
			statusCode = ex.getStatusCode().value();
			message = ex.getResponseBodyAsString();
			
			log.warn("HTTP Status: {},  Response body: {}", statusCode, message);
		}
		
		log.error(e.getMessage(), e);
		return new HttpConnectorException(statusCode, message);
	}
}
