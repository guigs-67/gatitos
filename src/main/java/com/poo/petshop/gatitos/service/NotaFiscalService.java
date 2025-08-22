package com.poo.petshop.gatitos.service;

import com.poo.petshop.gatitos.model.NotaFiscal.NotaFiscal;
import com.poo.petshop.gatitos.model.Cliente.Cliente;
import java.util.HashMap;
import java.util.Map;
public class NotaFiscalService {

    //Atributo banco de notas: A nosso banco de Nota fiscais: Map = Para cada id e nota fiscal correspondente é criado um objeto do tipo HashMap (garante que a busca pelas nossas notas fiscais seja em tempo constante)
    private final Map<Integer, NotaFiscal> bancoDeNotas = new HashMap<>();
    //Contabilizador do ID
    private int proximoId = 1;

    //Metodo para criar nossa nota fiscal.
    public NotaFiscal criar(Cliente cliente) {
        NotaFiscal novaNota = new NotaFiscal(cliente);
        //Atribui o ID atual
        novaNota.setId(proximoId);
        //Coloca na nossa
        bancoDeNotas.put(proximoId, novaNota);
        proximoId++;
        return novaNota;
    }

    //Metodo para buscar uma nota pelo ID.
    public NotaFiscal buscarPorId(int id) {
    return bancoDeNotas.get(id); 
    }
}