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

        //cria uma nova nota fiscal, tendo incialmente apenas o cliente como parâmetro
        NotaFiscal novaNota = new NotaFiscal(cliente);
        
        // atribui o id dela,usando o próximo diponível
        novaNota.setId(proximoId);
        
        // adiciona em uma lista em memória, quando der refresh, ela se perde
        bancoDeNotas.add(novaNota); 
        
        // Incrementa o contador para o próximo ID
        proximoId++;
        
        //Retorna a nota que acabamos de criar e salvar
        return novaNota;
    }

     // Inicia a nota fiscal vazia, buscando o cliente pelo cpf
     public NotaFiscal iniciarNotaFiscal(String cpfCliente) {
        Cliente cliente = clienteService.buscarClientePorCPF(cpfCliente);
        if (cliente == null) {
            throw new RuntimeException("Cliente com CPF " + cpfCliente + " não foi localizado para iniciar a nota.");
        }
        // usa o método para criar a nota, buscando o cpf do cliente
        return this.criar(cliente);
    }

    //adiciona um serviço a nota fiscal ja existente, incrementando ela
    public NotaFiscal adicionarServicoANota(int idNota, String nomeServico) {
        // Modifica a lista original
        NotaFiscal notaParaAtualizar = bancoDeNotas.stream()
                .filter(nota -> nota.getId() == idNota)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nota Fiscal com ID: " + idNota + " não foi encontrada!"));

        // método para buscar o nome do serviço e o colcoar na nota 
        Servicos servico = servicosService.buscarServicoPorNome(nomeServico);
        notaParaAtualizar.adicionarServico(servico);
        
        return new NotaFiscal(notaParaAtualizar); // Retorna uma cópia da nota atualizada
    }

    //retorna uma cópia da nota encontrada, para garantir o encapsulamento
    public NotaFiscal buscarCopiaPorId(int id) {
    for (NotaFiscal nota : bancoDeNotas) {
        if (nota.getId() == id) {
            return new NotaFiscal(nota); 
        }
    }
    throw new RuntimeException("ERRO!!! Nota Fiscal com ID: " + id + " não foi encontrada!");
    }
}