    package com.poo.petshop.gatitos.controller;

    import com.poo.petshop.gatitos.model.Animal.Animal;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;

    public class AnimalController {

        private List<Animal> animais;
        private Scanner scanner;

        public AnimalController() {
            this.animais = new ArrayList<>();
            this.scanner = new Scanner(System.in);
        }

        // Cadastrar animal 
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
            animais.add(animal);
            System.out.println("Animal cadastrado com sucesso!");
        }

        //função para remover animal pelo nome
        public void removerAnimal(String nome) {
		Animal animal = buscarAnimalPorNome(nome);
		if (animal != null) {
			animais.remove(animal);
			System.out.println("Animal removido com sucesso!");
		} else {
			System.out.println("Não foi possível remover o animal, pois ele não foi encontrado.");
		}
	}

        public List<Animal> listarAnimais() {
        return new ArrayList<>(animais); // Cópia da lista de animais para evitar modificações externas, respeitando o encapsulamento  
        }

        // Buscar animal pelo nome
        public Animal buscarAnimalPorNome(String nome) {
            for (Animal animal : animais) {
                if (animal.getNome().equalsIgnoreCase(nome)) {
                    return animal;
                }
            }
            System.out.println("Animal não encontrado: " + nome);
            return null;
        }
    }
