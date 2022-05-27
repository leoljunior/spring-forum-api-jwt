package com.leonardo.forumapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardo.forumapi.models.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
