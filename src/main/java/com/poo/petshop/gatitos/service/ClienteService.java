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
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private List<Cliente> clientes;
    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public ClienteService() {
        this.clientes = new ArrayList<>();
        carregarClientesDoArquivo();
    }
    
    // Valida um CPF, verificando se não é nulo, vazio e se possui 11 dígitos.
    private void validarCPF(String cpf) throws IOException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IOException("O CPF não pode ser nulo ou vazio.");
        }
        // Remove caracteres diferentes de 0-9 para facilitar a validação evitando ler coisas como '.' ou '-'
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        
        if (cpfNumerico.length() != 11) {
            throw new IOException("O CPF deve conter exatamente 11 dígitos. Valor recebido: " + cpf);
        }
    }
    
    // Verifica se um CPF já está cadastrado no sistema para evitar duplicidade.
    private void verificarCPFDuplicado(String cpf) throws IOException {
        if (buscarClientePorCPF(cpf) != null) {
            throw new IOException("Já existe um cliente cadastrado com o CPF: " + cpf);
        }
    }

    //verifica se nome é nulo ou se tem menos de 2 carctetres, acho que não existe ningúem com um nome com uma letra só
     private void validarNome(String nome) throws IOException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IOException("O nome não pode ser nulo ou vazio.");
        }
        if (nome.trim().length() < 2) {
            throw new IOException("O nome deve ter pelo menos 2 caracteres.");
        }
    }

    //verifica se tem algo no endereço, não conseguir pensar em outras validações
    private void validarEndereco(String endereco) throws IOException {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IOException("O endereço não pode ser nulo ou vazio.");
        }
    }

    //verifica se é vazio e se tem pelo menos 8 numeros
    private void validarTelefone(String telefone) throws IOException {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IOException("O telefone não pode ser nulo ou vazio.");
        }
        if (telefone.replaceAll("[^0-9]", "").length() < 8) {
             throw new IOException("O telefone parece ser inválido. Deve conter pelo menos 8 dígitos.");
        }
    }



    public String cadastrarCliente(Cliente cliente) {
    try {
        // Valida o CPF,nome e endereço antes de cadastrar
        validarCPF(cliente.getCPF());
        verificarCPFDuplicado(cliente.getCPF());
        validarNome(cliente.getNome());
        validarTelefone(cliente.getTelefone());
        validarEndereco(cliente.getEndereco());

        clientes.add(cliente);
        salvarClientesNoArquivo();
        
        return null; 
        
    } catch (IOException e) {
        // caso tenha algum erro ele retornará
        return e.getMessage(); //Retorna a string de erro em caso de falha, para usarmos na controller
    }
}

    public String atualizarCliente(Cliente clienteExistente, String novoNome, String novoEndereco, String novoTelefone) {
    try {
        // Valida o novo nome e enderço antes de salvar
        validarNome(novoNome);
        validarEndereco(novoEndereco);
        validarTelefone(novoTelefone);

        clienteExistente.setNome(novoNome);
        clienteExistente.setEndereco(novoEndereco);
        clienteExistente.setTelefone(novoTelefone);
        salvarClientesNoArquivo();
        return null; // Retorna null em caso de sucesso
    } catch (IOException e) {
        return e.getMessage(); // Retorna a mensagem de erro em caso de falha
    }
}

    public String removerCliente(String cpf) {
    Cliente cliente = buscarClientePorCPF(cpf);
    if (cliente != null) {
        clientes.remove(cliente);
        salvarClientesNoArquivo();
        return null; // Retorna null em caso de sucesso
    } else {
        // Retorna a mensagem de erro no front
        return "Erro ao remover: Cliente com CPF " + cpf + " não encontrado.";
    }
}

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente buscarClientePorCPF(String cpf) {
        if (cpf == null) return null;
        for (Cliente cliente : clientes) {
            if (cliente.getCPF().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }


    private void salvarClientesNoArquivo() {
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