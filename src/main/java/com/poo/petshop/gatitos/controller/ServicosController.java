package com.poo.petshop.gatitos.controller;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import com.poo.petshop.gatitos.model.Serviços.Servicos;
import com.poo.petshop.gatitos.model.Serviços.Estetico;
import com.poo.petshop.gatitos.model.Serviços.Saude;

public class ServicosController {
	private List<Servicos> servicos;
	private Scanner SC;

	// Construtor que inicializa um arrayList para armazenar os serviços vazio.
	public ServicosController() {
		this.servicos = new ArrayList<>();
		this.SC = new Scanner(System.in);
	}

	/* Método para cadastrar serviço (TEM QUE REFAZER LEVANDO EM CONTA O HTML (??? nao sei fazer, vou pesquisar e refaço ele)
	public void cadastrarServico() {
		System.out.print("Digite o nome do serviço: ");
		String nome = SC.nextLine();
		System.out.print("Digite o preço do serviço: ");
		BigDecimal preco = new BigDecimal(SC.nextLine());

		System.out.println("Escolha o tipo de serviço (1 - Estético, 2 - Saúde): ");
		int tipo = Integer.parseInt(SC.nextLine());

		if (tipo == 1) {
			System.out.print("Inclui banho? (true/false): ");
			boolean incluiBanho = Boolean.parseBoolean(SC.nextLine());
			System.out.print("Inclui tosa? (true/false): ");
			boolean incluiTosa = Boolean.parseBoolean(SC.nextLine());
			servicos.add(new Estetico(nome, preco, incluiBanho, incluiTosa));
		} else if (tipo == 2) {
			System.out.print("Consulta com especialista? (true/false): ");
			boolean consultaEspecialista = Boolean.parseBoolean(SC.nextLine());
			System.out.print("Vacina? (true/false): ");
			boolean vacina = Boolean.parseBoolean(SC.nextLine());
			servicos.add(new Saude(nome, preco, consultaEspecialista, vacina));
		} else {
			System.out.println("Tipo de serviço inválido.");
		}
		
		System.out.println("Serviço cadastrado com sucesso!");
	} */

	// Método para listar serviços
	public List<Servicos> listarServicos() {
		return servicos;
	}
	
}