package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.Cliente.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private List<Cliente> clientes;
    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public ClienteService() {
        this.clientes = new ArrayList<>();
        carregarClientesDoArquivo();
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
        salvarClientesNoArquivo();
    }

    public void atualizarCliente(Cliente clienteExistente, String novoNome, String novoEndereco, String novoTelefone) {
        clienteExistente.setNome(novoNome);
        clienteExistente.setEndereco(novoEndereco);
        clienteExistente.setTelefone(novoTelefone);
        salvarClientesNoArquivo();
    }

    public void removerCliente(String cpf) {
        Cliente cliente = buscarClientePorCPF(cpf);
        if (cliente != null) {
            clientes.remove(cliente);
            salvarClientesNoArquivo();
        }
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes); // Retorna uma cópia para proteger a lista original
    }

    public Cliente buscarClientePorCPF(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCPF().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    private void salvarClientesNoArquivo() { // Escreve a lista de clientes no arquivo
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CLIENTES), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Cliente cliente : clientes) {
                String linha = String.join(";", cliente.getCPF(), cliente.getNome(), cliente.getEndereco(), cliente.getTelefone());
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes no arquivo: " + e.getMessage());
        }
    }

    private void carregarClientesDoArquivo() { // Se existir, lê o arquivo e popula a lista de clientes
        if (!Files.exists(Paths.get(ARQUIVO_CLIENTES))) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO_CLIENTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    Cliente cliente = new Cliente(dados[0], dados[2], dados[3], dados[1]);
                    this.clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar clientes do arquivo: " + e.getMessage());
        }
    }
}