package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.model.NotaFiscal.NotaFiscal;
import com.poo.petshop.gatitos.model.Serviços.Servicos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaFiscalService {

    private final List<NotaFiscal> bancoDeNotas = new ArrayList<>();
    private int proximoId = 1;

    private final ClienteService clienteService;
    private final ServicosService servicosService;

    public NotaFiscalService(ClienteService clienteService, ServicosService servicosService) {
        this.clienteService = clienteService;
        this.servicosService = servicosService;
    }

    private NotaFiscal criar(Cliente cliente) {
    //cria uma nova nota fiscal
    NotaFiscal novaNota = new NotaFiscal(cliente);
    
    // atribui o id dela,usando o próximo diponível
    novaNota.setId(proximoId);
    
    // adiciona em uma lista em memória
    bancoDeNotas.add(novaNota); 
    
    // 4. Incrementa o contador para o próximo ID
    proximoId++;
    
    //Retorna a nota que acabamos de criar e salvar
    return novaNota;
}

    //gerar nota fiscal pegando o cliente que esta comprando e quais serviços ele está comprando
    public NotaFiscal gerarNotaParaServico(String cpfCliente, String nomeServico) {
    Cliente cliente = clienteService.buscarClientePorCPF(cpfCliente);
    if (cliente == null) {
        throw new RuntimeException("Cliente com CPF " + cpfCliente + " não foi localizado.");
    }

    //pega o serviço solicitado
    Servicos servico = servicosService.buscarServicoPorNome(nomeServico);
     
    //cria uma nota fiscal
    NotaFiscal novaNota = this.criar(cliente);

    //pega o serviço que pegamos e põe na nota
    novaNota.adicionarServico(servico);

    // retorna a nota
    return novaNota;
    }

    public NotaFiscal buscarCopiaPorId(int id) {  //proucura uma nota fiscal por id e retorna uma cópia dela
    return bancoDeNotas.stream()
        .filter(nota -> nota.getId() == id)
        .findFirst()
        .map(NotaFiscal::new)  
        .orElseThrow(() -> new RuntimeException("ERRO!!! Nota Fiscal com ID: " + id + " não foi encontrada!"));
}
}