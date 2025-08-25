package com.poo.petshop.gatitos.controller;

import com.poo.petshop.gatitos.model.Animal.Animal;
import com.poo.petshop.gatitos.service.AnimalService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animais") // URL base para os animais
public class AnimalController {

    private final AnimalService animalService;

    // Injeção de dependência
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // Cadastrar um novo animal
    @PostMapping
    public ResponseEntity<Animal> cadastrarAnimal(@RequestBody Animal animal) {
        animalService.cadastrarAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    // Listar os animais de um cliente específico pelo CPF
    @GetMapping("/cliente/{cpf}")
    public List<Animal> listarAnimaisPorCliente(@PathVariable String cpf) {
        // @PathVariable pega o valor {cpf} da URL
        return animalService.listarAnimaisPorDono(cpf);
    }
}