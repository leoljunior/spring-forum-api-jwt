package com.leonardo.forumapi.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.leonardo.forumapi.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}") //fazer injeção da propriedade do application properties
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authentication) {
		
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal(); //pegar o usuario
		
		return Jwts.builder() //chama builder para setar as infos de construção do token
				.setIssuer("API Forum") //quem esta gerando o token(aplicação)
				.setSubject(usuarioLogado.getId().toString()) //quem é o dono do token(usuario)
				.setIssuedAt(hoje) //data geração token
				.setExpiration(dataExpiracao) //data expiração do token
				.signWith(SignatureAlgorithm.HS256, secret) //indica o algoritmo de criptografia e senha
				.compact();
	}

	public boolean isTokenValido(String token) {
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token); //o parseClaimsJws retorna o objeto se token for valido, se for invalido ele lançça exception, por isso o block try catch
			return true;
		} catch (Exception e) {
			return false;
		}	
		
	}

	public Long getIdUsuario(String token) {
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody(); //pegando o body para pegar a id do usuario
		return Long.parseLong(body.getSubject()); //pegando o id do usuario
	}	
	
}
