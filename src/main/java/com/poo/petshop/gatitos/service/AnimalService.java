package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.Animal.Animal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service 
public class AnimalService {

    private List<Animal> animais;
    private static final String ARQUIVO_ANIMAIS = "animais.txt";

    public AnimalService() {
        this.animais = new ArrayList<>();
        carregarAnimaisDoArquivo();
    }

    public void cadastrarAnimal(Animal animal) {
        this.animais.add(animal);
        salvarAnimaisNoArquivo();
    }

    // Busca todos os animais que pertencem a um determinado CPF
    public List<Animal> listarAnimaisPorDono(String cpfDono) {
        return this.animais.stream()
            .filter(animal -> animal.getCpfDono().equals(cpfDono))
            .collect(Collectors.toList());
    }

    private void salvarAnimaisNoArquivo() { // Salva os animais no formato: cpfDono;nome;peso;porte;especie;raca;sexo
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_ANIMAIS), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Animal animal : animais) {
                String linha = String.join(";",
                    animal.getCpfDono(),
                    animal.getNome(),
                    String.valueOf(animal.getPeso()),
                    animal.getPorte(),
                    animal.getEspecie(),
                    animal.getRaca(),
                    animal.getSexo()
                );
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar animais no arquivo: " + e.getMessage());
        }
    }

    private void carregarAnimaisDoArquivo() { // Recria o objeto Animal a partir dos dados do arquivo
        if (!Files.exists(Paths.get(ARQUIVO_ANIMAIS))) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO_ANIMAIS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    Animal animal = new Animal(
                        dados[1],
                        Integer.parseInt(dados[2]),
                        dados[3], 
                        dados[4],
                        dados[5],
                        dados[6],
                        dados[0]
                    );
                    this.animais.add(animal);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar animais do arquivo: " + e.getMessage());
        }
    }
}