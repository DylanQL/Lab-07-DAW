package com.cjava.ejemplo.modelo.daos;

import com.cjava.ejemplo.modelo.entidades.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface AlumnoRepository extends CrudRepository<Alumno, Integer> {
}