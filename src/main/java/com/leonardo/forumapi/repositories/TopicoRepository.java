package com.leonardo.forumapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardo.forumapi.models.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByCursoNome(String nomeCurso);

}
