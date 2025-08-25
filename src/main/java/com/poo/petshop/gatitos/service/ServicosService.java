package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.Serviços.Estetico;
import com.poo.petshop.gatitos.model.Serviços.PacoteDeServicos;
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

    private void inicializarCatalogo() {
        // Serviços criados usando os métodos de factory
        catalogoServicos.add(criarServicoEstetico("Banho", BigDecimal.ZERO, true, false));
        catalogoServicos.add(criarServicoEstetico("Tosa", BigDecimal.ZERO, false, true));
        catalogoServicos.add(criarServicoEstetico("Banho e Tosa", BigDecimal.ZERO, true, true));
        catalogoServicos.add(criarServicoSaude("Consulta Geral", BigDecimal.ZERO, true, false));
        catalogoServicos.add(criarServicoSaude("Aplicação de Vacina", BigDecimal.ZERO, false, true));
        
        // Adiciona o pacote pré-definido ao catálogo
        catalogoServicos.add(montarPacoteDiaDeSpa());
    }

    public List<Servicos> listarServicosDisponiveis() {
        return new ArrayList<>(catalogoServicos);
    }
    
    // Monta um pacote de serviços "Dia de Spa" usando o padrão Composite.
    public PacoteDeServicos montarPacoteDiaDeSpa() {
        // Cria o pacote
        PacoteDeServicos pacoteSpa = new PacoteDeServicos("Pacote Dia de Spa");

        // Cria os serviços individuais usando os métodos desta classe
        Servicos banho = criarServicoEstetico("Banho Premium", BigDecimal.ZERO, true, false);
        Servicos tosa = criarServicoEstetico("Tosa Especial", BigDecimal.ZERO, false, true);

        // Adiciona os serviços ao pacote
        pacoteSpa.adicionar(banho);
        pacoteSpa.adicionar(tosa);

        return pacoteSpa;
    }

    //metodo para receber o serviço que o cliente vai querer e usar ele posteriormente na nota fiscal
    public Servicos buscarServicoPorNome(String nome) {
    // Percorre a lista de serviços
    for (Servicos servico : this.catalogoServicos) {
        // Compara o nome do serviço (ignorando maiúsculas/minúsculas)
        if (servico.getNome().equalsIgnoreCase(nome)) {
            return servico; // Retorna o serviço se encontrar
        }
    }
    // Se o loop terminar e não achar nada, retorna um erro
    throw new RuntimeException("Serviço com o nome '" + nome + "' não encontrado no catálogo.");
}

    // Métodos de Criação (Factory Methods)
    // Cria uma instância de um serviço Estético.
    private Servicos criarServicoEstetico(String nome, BigDecimal precoBase, boolean incluiBanho, boolean incluiTosa) {
        return new Estetico(nome, precoBase, incluiBanho, incluiTosa);
    }

    // Cria uma instância de um serviço de Saúde.
    private Servicos criarServicoSaude(String nome, BigDecimal precoBase, boolean consultaEspecialista, boolean vacina) {
        return new Saude(nome, precoBase, consultaEspecialista, vacina);
    }
}