package com.poo.petshop.gatitos;

import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClienteServiceTests {

    private ClienteService clienteService;
    private final String ARQUIVO_TESTE = "clientes_teste.txt";

    // Este método é executado ANTES de cada teste
    @BeforeEach
    public void setUp() throws IOException {
        // Apaga o arquivo de teste anterior para garantir que cada teste comece do zero
        Files.deleteIfExists(Paths.get(ARQUIVO_TESTE));
        // Instancia o service para o teste
        clienteService = new ClienteService();
    }

    // Teste unitário número 1: Cadastro de cliente com CPF válido
    @Test
    public void deveCadastrarClienteComCPFValido() { 
        Cliente cliente = new Cliente("12345678901", "Rua Teste", "99999999", "Cliente Válido");
        String resultado = clienteService.cadastrarCliente(cliente);
        assertNull(resultado, "O cadastro de um cliente com CPF válido não deve retornar mensagem de erro.");
    }

    // Teste unitário número 2: Cadastro de cliente com CPF inválido
    @Test
    public void naoDeveCadastrarClienteComCPFInvalido() {
        Cliente cliente = new Cliente("123", "Rua Teste", "99999999", "Cliente Inválido");
        String resultado = clienteService.cadastrarCliente(cliente);
        assertNotNull(resultado, "O cadastro de um cliente com CPF inválido deve retornar uma mensagem de erro.");
        assertTrue(resultado.contains("O CPF deve conter exatamente 11 dígitos"), "A mensagem de erro para CPF inválido está incorreta.");
    }
}