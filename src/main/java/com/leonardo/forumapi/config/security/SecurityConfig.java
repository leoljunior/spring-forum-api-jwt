package com.leonardo.forumapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.leonardo.forumapi.repositories.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final String[] PUBLIC_URLS = { "/topicos", "/topicos/*" };

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
//	Configs de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
//	Configs de autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
			.antMatchers(HttpMethod.POST, "/auth", "/users").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable() //como a autenticação é via token ela esta livre desse ataque, logo podemos desabilitar, assim o spring security nao faz a validação do token csrf(cross-site request forgery)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //indica que a sessão é stateless
			.and().addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class); //indica pro spring que nosso filtro vem antes do filtro interno dele
			
			
//		formLogin() spring gerar formulario de autenticação, esse metodo cria sessão(salva estado)
	}
	
	
//	Configs de recursos estaticos(js, css, imgs)
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
}
