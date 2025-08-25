package com.poo.petshop.gatitos.controller;

import com.poo.petshop.gatitos.model.NotaFiscal.NotaFiscal;
import com.poo.petshop.gatitos.service.NotaFiscalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/notas-fiscais") 
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    public NotaFiscalController(NotaFiscalService notaFiscalService) {
        this.notaFiscalService = notaFiscalService;
    }

    // Criar uma nova nota fiscal usando um serviço
    @PostMapping
    public ResponseEntity<?> criarNotaFiscal(@RequestBody CriarNotaRequest request) {
        try {
             // chama a função que incrementa a nota na service
            NotaFiscal novaNota = notaFiscalService.gerarNotaParaServico(request.getCpfCliente(), request.getServico());
            // Se tudo der certo, retorna a nota fiscal completa para o Js
            return ResponseEntity.ok(novaNota); 
        } catch (RuntimeException e) {
        
            // se der errado, retorna um erro
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar uma nota fiscal por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNotaPorId(@PathVariable int id) {
        try {
            NotaFiscal nota = notaFiscalService.buscarCopiaPorId(id);
            return ResponseEntity.ok(nota);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // nada foi encontrado
        }
    }
}

// feito apenas para servir como uma DTO(um jeito organizado de passar os dados) para o front, pra facilitar a exposição desses dados
class CriarNotaRequest {
    private String cpfCliente;
    private String servico;

    // Getters e Setters
    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }
}