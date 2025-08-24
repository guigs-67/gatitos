package com.poo.petshop.gatitos.controller;

import java.util.List;
import java.util.Scanner;
import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.service.ClienteService;

public class ClienteController {
    
    private ClienteService clienteService;
    private Scanner sc;

    public ClienteController() { 
        this.clienteService = new ClienteService(); // Cria a instância do Service
        this.sc = new Scanner(System.in);
    }

    public void cadastrarCliente() {
    	// 1. Pede e lê cada informação do usuário, uma por uma.
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = sc.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = sc.nextLine();
        System.out.print("Digite o nome do cliente: ");
        String nome = sc.nextLine();

        Cliente novoCliente = new Cliente(cpf, endereco, telefone, nome);
        clienteService.cadastrarCliente(novoCliente); // Encarrega a ação para o Service
        
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public List<Cliente> listarClientes() {
        return clienteService.listarClientes(); // Manda para o Service e retorna a lista
    }
    
    public Cliente buscarClientePorCPF(String cpf) {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        }
        return cliente;
    }
    
    public void atualizarCliente(String cpf) {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        if (cliente != null) {
            System.out.print("Digite o novo endereço do cliente: ");
            String endereco = sc.nextLine();
            System.out.print("Digite o novo telefone do cliente: ");
            String telefone = sc.nextLine();
            System.out.print("Digite o novo nome do cliente: ");
            String nome = sc.nextLine();

            clienteService.atualizarCliente(cliente, nome, endereco, telefone); // Encarrega para o Service
            System.out.println("Cliente atualizado com sucesso!");
        } 
        else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    public void removerCliente(String cpf) {
        if (clienteService.buscarClientePorCPF(cpf) != null) {
            clienteService.removerCliente(cpf);
            System.out.println("Cliente removido com sucesso!");
        } 
        else {
            System.out.println("Cliente não encontrado.");
        }
    }
}