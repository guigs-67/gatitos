package com.poo.petshop.gatitos.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.poo.petshop.gatitos.model.Serviços.Servicos;
import com.poo.petshop.gatitos.service.ServicosService;

@RestController
@RequestMapping("/api/servicos") // URL base para os serviços
public class ServicosController {

    private final ServicosService servicosService;

    // Injeção de dependência
    public ServicosController(ServicosService servicosService) {
        this.servicosService = servicosService;
    }

    // Listar todos os serviços disponíveis no catálogo
    @GetMapping
    public List<Servicos> listarServicos() {
        return servicosService.listarServicosDisponiveis();
    }
}