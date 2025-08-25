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

    // buscar um cliente específico usando o cpf como parâmetro
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> getClientePorCPF(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        
        if (cliente != null) {
            // se o cliente for encontrado, retorna os dados e o status 200 OK
            return ResponseEntity.ok(cliente);
        } else {
            // se o cliente não for encontrado, retorna o status 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    
   // atualizar um cliente usando cpf como parametro
    @PutMapping("/{cpf}")
    public ResponseEntity<String> atualizarCliente(@PathVariable String cpf, @RequestBody Cliente dadosAtualizados) {
        Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente com CPF " + cpf + " não encontrado.");
        }
        // guarda o resultado da tentativa de atualização
        String resultado = clienteService.atualizarCliente(
            clienteExistente, 
            dadosAtualizados.getNome(), 
            dadosAtualizados.getEndereco(), 
            dadosAtualizados.getTelefone()
        );
        // Se o resultado for nulo, deu tudo certo
        if (resultado == null) {
            return ResponseEntity.ok().body("Cliente atualizado com sucesso!");
        } else {
            // Se deu errado, a String de resultado terá uma resposta de erro
            return ResponseEntity.badRequest().body(resultado);
        }
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
    // remover cliente usando cpf como parametro
     @DeleteMapping("/{cpf}")
        public ResponseEntity<String> removerCliente(@PathVariable String cpf) {
             // guarda o resultado da tentativa de remoção
        String resultado = clienteService.removerCliente(cpf);
         // Se o resultado for nulo, deu tudo certo
        if (resultado == null) {
            return ResponseEntity.ok().body("Cliente removido com sucesso!");
        } else {
            // Se deu errado, a String de resultado terá uma resposta de erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
    }
}