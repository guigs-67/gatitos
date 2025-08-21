package com.poo.petshop.gatitos.controller;

import com.poo.petshop.gatitos.model.Animal.Animal;
import com.poo.petshop.gatitos.service.AnimalService;

import java.util.List;
import java.util.Scanner;

public class AnimalController {

    private AnimalService animalService;
    private Scanner scanner;

    public AnimalController() {
        this.animalService = new AnimalService();
        this.scanner = new Scanner(System.in);
    }

    // Cadastro usando o construtor definido na model
    public void cadastrarAnimal() {
        System.out.print("Digite o nome do animal: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o peso do animal: ");
        int peso = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o porte do animal: ");
        String porte = scanner.nextLine();
        System.out.print("Digite a espécie do animal: ");
        String especie = scanner.nextLine();
        System.out.print("Digite a raça do animal: ");
        String raca = scanner.nextLine();
        System.out.print("Digite o sexo do animal: ");
        String sexo = scanner.nextLine();

        Animal animal = new Animal(nome, peso, porte, especie, raca, sexo);
        animalService.cadastrarAnimal(animal);
    }

    // Chamada da função de remoção definida na Service
    public void removerAnimal(String nome) {
        animalService.removerAnimal(nome);
    }

    // Chamada da função de Listagem definida na Service
    public void listarAnimais() {
        List<Animal> animais = animalService.listarAnimais();
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
        } else {
            for (Animal animal : animais) {
                System.out.println("Nome: " + animal.getNome() +
                        ", Peso: " + animal.getPeso() +
                        ", Porte: " + animal.getPorte() +
                        ", Espécie: " + animal.getEspecie() +
                        ", Raça: " + animal.getRaca() +
                        ", Sexo: " + animal.getSexo());
            }
        }
    }

    // Chamada da função de Busca definida na Service
    public void buscarAnimal(String nome) {
        Animal animal = animalService.buscarAnimalPorNome(nome);
        if (animal != null) {
            System.out.println("Animal encontrado: " + animal.getNome());
        } else {
            System.out.println("Animal não encontrado.");
        }
    }
}
