package com.cjava.ejemplo.controladores;

import com.cjava.ejemplo.modelo.entidades.Curso;
import com.cjava.ejemplo.servicios.CursoService;
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
@SessionAttributes("curso")
public class CursoController {
    @Autowired
    private CursoService servicio;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Cursos  CJAVA");
        model.addAttribute("cursos", servicio.listar());
        return "listarView";
    }

    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model) {
        Curso curso = new Curso();
        model.put("curso", curso);
        model.put("titulo", "Formulario de Curso");
        return "formView";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        Curso curso = null;
        if (id > 0) {
            curso = servicio.buscar(id);
        } else {
            return "redirect:/listar";
        }
        model.put("curso", curso);
        model.put("titulo", "Editar Curso");
        return "formView";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Curso curso, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Curso");
            return "formView";
        }
        servicio.grabar(curso);
        status.setComplete();
        return "redirect:listar";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id) {
        if (id > 0) {
            servicio.eliminar(id);
        }
        return "redirect:/listar";
    }

    @RequestMapping(value = "/verCursos", method = RequestMethod.GET)
    public ModelAndView verCursos(@RequestParam(name = "format", required = false) String format) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cursos", servicio.listar());
        modelAndView.addObject("titulo", "Lista de cursos");

        if ("pdf".equalsIgnoreCase(format)) {
            modelAndView.setViewName("curso/ver.pdf");
        } else if ("xlsx".equalsIgnoreCase(format)) {
            modelAndView.setViewName("curso/ver.xlsx");
        } else {
            modelAndView.setViewName("curso/ver");
        }

        return modelAndView;
    }
}