package com.poo.petshop.gatitos.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.service.ClienteService;

@RestController // Transforma a classe em um Controller Web
@RequestMapping("/api/clientes") // Define a URL base para todos os métodos desta classe
public class ClienteController {
    
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) { 
        this.clienteService = clienteService;
    }

    // Método para listar todos os clientes
    @GetMapping // Responde a requisições GET para "/api/clientes"
    public List<Cliente> listarClientes() {
        // O Spring converterá esta lista em JSON automaticamente
        return clienteService.listarClientes();
    }
    
    // Método para cadastrar um novo cliente
    @PostMapping // Responde a requisições POST para "/api/clientes"
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        // A anotação @RequestBody converte o JSON vindo do front-end em um objeto Cliente
        clienteService.cadastrarCliente(cliente);
        
        // Retorna o cliente salvo e o status HTTP 201 (Created)
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
}