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
 
    // Retorna true se removeu com sucesso, false se não encontrou
    public boolean removerAnimal(String nome) {
        Animal animal = buscarAnimalPorNome(nome); 
        if (animal != null) {
            animais.remove(animal); 
            System.out.println("Animal removido com sucesso!");
            return true;
        }
        System.out.println(" Animal não encontrado para remoção: " + nome);
        return false;
    }

    // Retorna uma cópia da lista para não ter risco do encapsulamento ser quebrado
    public List<Animal> listarAnimais() {
        return new ArrayList<>(animais);
    }

    // Faz comparação ignorando maiúsculas/minúsculas(equalsIgnoreCase)
    // Retorna o objeto Animal se encontrado, ou null caso contrário
    public Animal buscarAnimalPorNome(String nome) {
        for (Animal animal : animais) {
            if (animal.getNome().equalsIgnoreCase(nome)) {
                return animal;
            }
        }
        return null;
    }
}
