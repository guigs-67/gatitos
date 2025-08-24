package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.Animal.Animal;
import java.util.ArrayList;
import java.util.List;

// Classe de serviço que gerencia os animais do petshop
public class AnimalService {

    // Lista em memória que armazena os animais cadastrados
    private List<Animal> animais;

    // Construtor: inicia a lista de animais vazia, para o usuário começar a cadastrar
    public AnimalService() {
        this.animais = new ArrayList<>();
    }

    // Recebe um objeto Animal e adiciona na lista
    public void cadastrarAnimal(Animal animal) {
        animais.add(animal);
        System.out.println("Animal cadastrado com sucesso!");
    }

    // Retorna true se removeu com sucesso, false se não encontrou, fazendo todo processamento de remoção intermamente, sem "vazar" nenhum dado da lista
      public boolean removerAnimal(String nome) {
        for (int i = 0; i < animais.size(); i++) {
            if (animais.get(i).getNome().equalsIgnoreCase(nome)) {
                animais.remove(i);
                System.out.println("Animal removido com sucesso!");
                return true;
            }
        }
        System.out.println("Animal não encontrado para remoção: " + nome);
        return false;
    }

    // Retorna uma lista de cópias dos animais cadastrados, pois assim, o usuário não terá acesso direto a lista original
   public List<Animal> listarAnimais() {
    List<Animal> copia = new ArrayList<>();
    for (Animal animal : animais) {
        copia.add(new Animal(
            animal.getNome(),
            animal.getPeso(),
            animal.getPorte(),
            animal.getEspecie(),    
            animal.getRaca(),
            animal.getSexo()
        ));
    }
    return copia;
}

    // Compara o nome dado, ao nome de cada um dos animias cadastrado e retorna uma cópia do objeto Animal que tiver o nome igual ao parâmetro
    public Animal buscarAnimalPorNome(String nome) {
        for (Animal animal : animais) {
            if (animal.getNome().equalsIgnoreCase(nome)) {
                // Retorna uma cópia do animal para preservar encapsulamento
                return new Animal(
                    animal.getNome(),
                    animal.getPeso(),
                    animal.getPorte(),
                    animal.getEspecie(),
                    animal.getRaca(),
                    animal.getSexo()
                );
            }
        }
        return null;
    }
}