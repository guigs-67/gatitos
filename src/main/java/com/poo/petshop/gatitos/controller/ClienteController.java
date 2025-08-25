package com.poo.petshop.gatitos.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) { 
        this.clienteService = clienteService;
    }

    // Método para listar todos os clientes (permanece o mesmo)
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody Cliente cliente) {
        // guarda o resultado da tentativa de cadastramento
        String resultado = clienteService.cadastrarCliente(cliente);
        // Se o resultado for nulo, deu tudo certo
        if (resultado == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
        } else {
            // Se deu errado, a String de resultado terá uma resposta de erro
            return ResponseEntity.badRequest().body(resultado);
        }
    }
}