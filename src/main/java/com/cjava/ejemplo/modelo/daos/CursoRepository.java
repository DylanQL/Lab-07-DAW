package com.cjava.ejemplo.modelo.daos;

import com.cjava.ejemplo.modelo.entidades.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Integer> {

}