package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.Serviços.Estetico;
import com.poo.petshop.gatitos.model.Serviços.Saude;
import com.poo.petshop.gatitos.model.Serviços.Servicos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServicosService {

    private final List<Servicos> catalogoServicos;

    public ServicosService() {
        this.catalogoServicos = new ArrayList<>();
        inicializarCatalogo();
    }

    // Cria o catálogo de serviços fixos do petshop
    private void inicializarCatalogo() {
        catalogoServicos.add(new Estetico("Banho", new BigDecimal("0"), true, false));
        catalogoServicos.add(new Estetico("Tosa", new BigDecimal("0"), false, true));
        catalogoServicos.add(new Estetico("Banho e Tosa", new BigDecimal("0"), true, true));
        catalogoServicos.add(new Saude("Consulta Geral", new BigDecimal("0"), true, false));
        catalogoServicos.add(new Saude("Aplicação de Vacina", new BigDecimal("0"), false, true));
    }

    // Retorna uma lista de todos os serviços disponíveis.
    public List<Servicos> listarServicosDisponiveis() {
        return new ArrayList<>(catalogoServicos);
    }
}