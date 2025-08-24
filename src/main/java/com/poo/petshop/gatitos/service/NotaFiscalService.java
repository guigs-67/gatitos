package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.NotaFiscal.NotaFiscal;
import com.poo.petshop.gatitos.model.Cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class NotaFiscalService {

    //Cria nossa lista de notas fiscais.
    private final List<NotaFiscal> bancoDeNotas = new ArrayList<>();
    //Contabilizador do ID
    private int proximoId = 1;

    public NotaFiscal criar(Cliente cliente) {
        NotaFiscal novaNota = new NotaFiscal(cliente);
        //Atribui o ID atual
        novaNota.setId(proximoId);
        //Coloca a nota na nossa lista;
        bancoDeNotas.add(novaNota); 
        //Atualiza o ID para a proxima nota
        proximoId++;
        return novaNota;
    }
    //Metodo para buscar uma nota pelo ID.
    public NotaFiscal buscarCopiaPorId(int id) {
        // Percorre a lista original para encontrar a nota
        for (NotaFiscal notaOriginal : this.bancoDeNotas) {
            //Se achar o id retorna o clone da nota original.
            if (notaOriginal.getId() == id) {
                //Polimorfismo de sobrecarga: mesma assinatura mas como o parametro é de outro tipo retorna a nota fiscal esperada (o clone).
                return new NotaFiscal(notaOriginal);
            }
        }
        //Se não achar o id da nota até o final do loop chama o tratamento de exceções:
        throw new RuntimeException("ERRO!!! Nota Fiscal com ID: " + id + " não foi encontrada!");
    }
}