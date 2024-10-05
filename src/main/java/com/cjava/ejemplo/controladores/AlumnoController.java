package com.cjava.ejemplo.controladores;

import com.cjava.ejemplo.modelo.entidades.Alumno;
import com.cjava.ejemplo.servicios.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {
    @Autowired
    private AlumnoService servicio;

    @RequestMapping(value = "/listarAlumnos", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Alumnos  CJAVA");
        model.addAttribute("alumnos", servicio.listar());
        return "listarAlumnosView";
    }

    @RequestMapping(value = "/formAlumno", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Alumno alumno = new Alumno();
        model.put("alumno", alumno);
        model.put("titulo", "Formulario de Alumno");
        return "formAlumnoView";
    }

    @RequestMapping(value = "/formAlumno/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        Alumno alumno = null;
        if (id > 0) {
            alumno = servicio.buscar(id);
        } else {
            return "redirect:/listarAlumnos";
        }
        model.put("alumno", alumno);
        model.put("titulo", "Editar Alumno");
        return "formAlumnoView";
    }

    @RequestMapping(value = "/formAlumno", method = RequestMethod.POST)
    public String guardar(@Valid Alumno alumno, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Alumno");
            return "formAlumnoView";
        }
        servicio.grabar(alumno);
        status.setComplete();
        return "redirect:listarAlumnos";
    }

    @RequestMapping(value = "/eliminarAlumno/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Integer id) {
        if (id > 0) {
            servicio.eliminar(id);
        }
        return "redirect:/listarAlumnos";
    }

    @RequestMapping(value = "/verAlumnos", method = RequestMethod.GET)
    public ModelAndView verAlumnos(@RequestParam(name = "format", required = false) String format) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumnos", servicio.listar());
        modelAndView.addObject("titulo", "Lista de alumnos");

        if ("pdf".equalsIgnoreCase(format)) {
            modelAndView.setViewName("alumno/ver.pdf");
        } else if ("xlsx".equalsIgnoreCase(format)) {
            modelAndView.setViewName("alumno/ver.xlsx");
        } else {
            modelAndView.setViewName("alumno/ver");
        }

        return modelAndView;
    }
}