package br.com.fiap.starter.http;

import br.com.fiap.starter.http.dto.HttpConnectDto;

public interface HttpConnectGateway {
	public String postWithRequestBodyMultipart(HttpConnectDto dto);
	public String postWhithRequestBody(HttpConnectDto dto);
	public String get(HttpConnectDto dto);
	public String patch(HttpConnectDto httpConnectDto);
}
