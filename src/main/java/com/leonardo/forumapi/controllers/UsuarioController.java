package com.leonardo.forumapi.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.leonardo.forumapi.controller.dtos.UsuarioDto;
import com.leonardo.forumapi.controller.forms.UsuarioForm;
import com.leonardo.forumapi.models.Usuario;
import com.leonardo.forumapi.repositories.UsuarioRepository;

@RestController
@RequestMapping("/users")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping
	public ResponseEntity<UsuarioDto> usuario(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
		
		Usuario usuario = usuarioForm.convert();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashSenha = bCryptPasswordEncoder.encode(usuario.getSenha());
		
		usuario.setSenha(hashSenha);
		
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/users").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
		
	}
	
}
