package com.cjava.ejemplo.servicios;

import com.cjava.ejemplo.modelo.entidades.Alumno;
import java.util.List;

public interface AlumnoService {
    List<Alumno> listar();
    void grabar(Alumno alumno);
    Alumno buscar(Integer id);
    void eliminar(Integer id);
}