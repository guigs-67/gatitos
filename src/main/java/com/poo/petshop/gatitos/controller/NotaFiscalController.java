package com.poo.petshop.gatitos.controller;

import com.poo.petshop.gatitos.service.NotaFiscalService;
import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.model.NotaFiscal.NotaFiscal;
import com.poo.petshop.gatitos.model.Serviços.PacoteDeServicos;
import com.poo.petshop.gatitos.service.ClienteService;
import com.poo.petshop.gatitos.service.ServicosService;

public class NotaFiscalController {
  
    //Objeto do tipo NotalFiscalService, o controller usará ele para solicitar os comandos aos service, o final garante que a conexão desse objeto com o service do controle seja permanente.
    private final NotaFiscalService notaFiscalService; 
    private final ServicosService servicoService;
    private final ClienteService clienteService;

    
    public NotaFiscalController(NotaFiscalService nfService, ServicosService servicoService, ClienteService clienteService) {
        this.notaFiscalService = nfService;
        this.servicoService = servicoService;
        this.clienteService = clienteService;
    }

    //Metodo para nosso padrão de projeto Composite, controller de nota fiscal irá solicitar aos services a criação de um pacote de servico estético.
    public NotaFiscal criarVendaComPacote(String cpfCliente) {
        try {
            //Cria um objeto cliente e faz ele apontar para o endereço do cliente com cpf do parametro usando o nosso metodo de buscar cliente do service de cliente.
            Cliente cliente = clienteService.buscarClientePorCPF(cpfCliente);
            if (cliente == null) {
                //Lança uma exceção se o cliente com o cpf informado não for encontrado.
                throw new RuntimeException("Cliente com CPF " + cpfCliente + " não foi localizado.");
            }
            
            //Se encontrar cria a nota fiscal base utilizando como parametro o objeto cliente que foi criado
            NotaFiscal nf = notaFiscalService.criar(cliente);
            //Monta o pacote
            PacoteDeServicos pacotePronto = servicoService.montarPacoteDiaDeSpa();
            // Passo 4: Adicionar o pacote à nota
            nf.adicionarServico(pacotePronto);
            return nf;

        } catch (RuntimeException erro) {
            System.out.println("ERRO: " + erro.getMessage());
            return null; // Retorna nulo em caso de erro.
        }
    }

    //Metodo para solicitar a impressão por ID de uma nota fiscal;
    public void imprimirNotaFiscalPeloId(int id) { 

        try {
            //Objeto nota encontrada -> vai apontar para o endereço da nota com o ID solicitado;
            NotaFiscal notaEncontrada = notaFiscalService.buscarCopiaPorId(id);
            //Se encontrou imprimi
            notaEncontrada.imprimir(); 

        } 
        catch (RuntimeException erro) {
            //Se o id não existir então chama nossa exceção e só irá tentar a impressão caso o id exista na nossa lista de notas fiscais.
            System.out.println(erro.getMessage());
        }
    }
}