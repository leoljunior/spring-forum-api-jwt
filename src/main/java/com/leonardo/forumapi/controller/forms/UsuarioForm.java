package com.leonardo.forumapi.controller.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.leonardo.forumapi.models.Usuario;

public class UsuarioForm {

	@NotNull @NotEmpty @Length(max = 30)
	private String nome;
	
	@NotNull @NotEmpty @Length(max = 50)
	private String email;
	
	@NotNull @NotEmpty
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario convert() {		
		return new Usuario(nome, email, senha);
	}
	
	
}
