package com.tienda.tienda.controller;

import com.tienda.tienda.service.ProductoService;
import com.tienda.tienda.service.CategoriaService;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ConsultaController(ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.getCategorias(false));
        return "/consultas/listado";
    }

    @PostMapping("/consultaDerivada")
    public String consultaDerivida(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.consultaDerivada(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("categorias", categoriaService.getCategorias(false));
        return "/consultas/listado";
    }

    @PostMapping("/consultaJPQL")
    public String consultaJPQL(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.consultaJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("categorias", categoriaService.getCategorias(false));
        return "/consultas/listado";
    }

    @PostMapping("/consultaSQL")
    public String consultaSQL(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.consultaSQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("categorias", categoriaService.getCategorias(false));
        return "/consultas/listado";
    }

    @PostMapping("/consultaAmpliada")
    public String consultaAmpliada(
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(defaultValue = "false") boolean soloDisponibles,
            Model model) {

        if (descripcion != null && descripcion.isBlank()) {
            descripcion = null;
        }

        var productos = productoService.consultaAmpliada(
                descripcion, idCategoria, soloDisponibles);

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.getCategorias(false));
        model.addAttribute("descripcion", descripcion);
        model.addAttribute("idCategoria", idCategoria);
        model.addAttribute("soloDisponibles", soloDisponibles);

        return "/consultas/listado";
    }
}
