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

    //Validações para cadastramento de animais:
    
    private void validarPeso(double peso){
        //verifica se o peso é positivo
        if (peso <= 0) {
            throw new IllegalArgumentException("O peso do animal precisa ser um número inteiro positivo e diferente de 0");
        }
        //verifica se não é infinito ou not a number
        if (Double.isNaN(peso) || Double.isInfinite(peso)) {
        throw new IllegalArgumentException("O peso fornecido não é um número válido!");
        }
        //verifica se é um número plausivel
        if (peso < 0.1 || peso > 100.0) {
            throw new IllegalArgumentException("Peso fora dos limites. Deve estar entre 0.1g (100 gramas) e 100kg (100 quilos)");
        }
    }
    private void validarPorte(String porte){
        //valida se foi preenchido
        if (porte == null || porte.trim().isEmpty()) {
            throw new IllegalArgumentException("O porte não pode ser nulo ou vazio");
        }
    }
    private void validarEspecie(String especie){
        //valida se foi preenchido
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("A especie não pode ser nula ou vazia");
        }
    }
    private void validarRaca(String raca){
        //valida se foi preenchido
        if (raca == null || raca.trim().isEmpty()) {
            throw new IllegalArgumentException("A raça não pode ser nula ou vazia");
        }
    }
    private void validarSexo(String sexo){
        //Valida se foi preenchido
        if (sexo == null || sexo.trim().isEmpty()) {
            throw new IllegalArgumentException("O sexo não pode ser nulo ou vazia");
        }
        //Formata para a entrada ficar maiúscula e remove espaços antes ou depois
        String sexoFormatado = sexo.trim().toUpperCase();
        //Verifica se ta dentro do padrão M ou F
        if (!sexoFormatado.equals("M") && !sexoFormatado.equals("F")) {
            throw new IllegalArgumentException("Sexo inválido. O valor deve ser 'M' ou 'F'.");
        }

    }
    public void cadastrarAnimal(Animal animal) {
        //chama as validações 
        validarPeso(animal.getPeso());
        validarPorte(animal.getPorte());
        validarEspecie(animal.getEspecie());
        validarRaca(animal.getRaca());
        validarSexo(animal.getSexo());
        
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
                        Double.parseDouble(dados[2]),
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