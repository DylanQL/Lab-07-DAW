package com.cjava.ejemplo.servicios;


import com.cjava.ejemplo.modelo.entidades.Curso;

import java.util.List;

public interface CursoService {

    public List<Curso> listar();

    public void grabar(Curso curso);

    public Curso buscar(Integer id);

    public void eliminar(Integer id);

}

