package com.poo.petshop.gatitos.controller;

import com.poo.petshop.gatitos.service.NotaFiscalService;
import com.poo.petshop.gatitos.model.NotaFiscal.NotaFiscal;

public class NotaFiscalController {

    //Objeto do tipo NotalFiscalService, o controller usará ele para solicitar os comandos aos service.
    private final NotaFiscalService notaFiscalService = new NotaFiscalService();

    //Metodo para solicitar a impressão por ID de uma nota fiscal;
    public void imprimirNotaFiscalPeloId(int id) { 
        //Objeto nota encontrada -> vai apontar para o endereço da nota com o ID solicitado;
        NotaFiscal notaEncontrada = notaFiscalService.buscarPorId(id);
        
        //Se o id não existir na lista o objeto apontará para NULL então precisa de um if para garantir que só irá tentar a impressão caso o id exista na nossa lista de notas fiscais.
        if (notaEncontrada != null) { 
            notaEncontrada.imprimir(); 
        } else {
            System.out.println("Não foi possível encontrar a Nota Fiscal com o id: " + id + ".");
        }
    }
}