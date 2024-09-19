package br.com.fiap.starter.token;

public interface TokenGateway {
	Object getInfoFromToken(String infoName, String token);
}
