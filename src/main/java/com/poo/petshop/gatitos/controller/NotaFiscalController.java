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

    // Inicia a nota fiscal vazia
    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarNotaFiscal(@RequestBody IniciarNotaRequest request) {
        try {
            NotaFiscal novaNota = notaFiscalService.iniciarNotaFiscal(request.getCpfCliente());
            return ResponseEntity.ok(novaNota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Adiciona um serviço a essa nota fiscal 
    @PostMapping("/{id}/adicionar-servico")
    public ResponseEntity<?> adicionarServico(@PathVariable int id, @RequestBody AdicionarServicoRequest request) {
        try {
            NotaFiscal notaAtualizada = notaFiscalService.adicionarServicoANota(id, request.getServico());
            return ResponseEntity.ok(notaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Pega a nota fiscal finalizada para exibi-la, usando seu id como parêmetro
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNotaPorId(@PathVariable int id) {
        try {
            NotaFiscal nota = notaFiscalService.buscarCopiaPorId(id);
            return ResponseEntity.ok(nota);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}

// feito apenas para servir como um pseudo DTO(um jeito organizado de passar os dados) para o front, pra facilitar a exposição desses dados

class IniciarNotaRequest {
    private String cpfCliente;
    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
}

class AdicionarServicoRequest {
    private String servico;
    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }
}