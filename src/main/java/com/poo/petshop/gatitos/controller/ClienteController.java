package com.poo.petshop.gatitos.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.poo.petshop.gatitos.model.Cliente.Cliente;

public class ClienteController {
	private List<Cliente> clientes;
	private Scanner SC;

	// Construtor que inicializa um arrayList para armazenar os clientes vazio.
	public ClienteController() { 
		this.clientes = new ArrayList<>();
		this.SC = new Scanner(System.in);
	}

	// Método para cadastrar
	public void cadastrarCliente() {
		System.out.print("Digite o CPF do cliente: ");
		String cpf = SC.nextLine();
		System.out.print("Digite o endereço do cliente: ");
		String endereco = SC.nextLine();
		System.out.print("Digite o telefone do cliente: ");
		String telefone = SC.nextLine();
		System.out.print("Digite o nome do cliente: ");
		String nome = SC.nextLine();

		Cliente cliente = new Cliente(cpf, endereco, telefone, nome);
		clientes.add(cliente);
		System.out.println("Cliente cadastrado com sucesso!");
	}

	// Método para listar.
	public List<Cliente> listarClientes() {
		return clientes;
	}
	
	// Método para buscar cliente por CPF.
	public Cliente buscarClientePorCPF(String cpf) {
		for (Cliente cliente : clientes) {
			if (cliente.getCPF().equals(cpf)) {
				return cliente;
			}
		}
		System.out.println("Cliente não encontrado.");
		return null;
	}
	
	// Método para atualizar cliente.
	public void atualizarCliente(String cpf) {
		Cliente cliente = buscarClientePorCPF(cpf);
		if (cliente != null) {
			System.out.print("Digite o novo endereço do cliente: ");
			String endereco = SC.nextLine();
			System.out.print("Digite o novo telefone do cliente: ");
			String telefone = SC.nextLine();
			System.out.print("Digite o novo nome do cliente: ");
			String nome = SC.nextLine();

			cliente.setEndereco(endereco);
			cliente.setTelefone(telefone);
			cliente.setNome(nome);
			System.out.println("Cliente atualizado com sucesso!");
		}
	}
	
	// Método para remover cliente.
	public void removerCliente(String cpf) {
		Cliente cliente = buscarClientePorCPF(cpf);
		if (cliente != null) {
			clientes.remove(cliente);
			System.out.println("Cliente removido com sucesso!");
		} else {
			System.out.println("Cliente não encontrado.");
		}
	}
}