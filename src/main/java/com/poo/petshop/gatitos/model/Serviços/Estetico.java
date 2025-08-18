package com.poo.petshop.gatitos.model.Serviços;
import java.math.BigDecimal;
import java.util.StringJoiner;

public class Estetico extends Servicos {

    private boolean incluiBanho;
    private boolean incluiTosa;

    public Estetico(String nome, BigDecimal preco, boolean incluiBanho, boolean incluiTosa){
        super(nome, preco);
        this.incluiBanho = incluiBanho;
        this.incluiTosa = incluiTosa;
    }

    @Override
    public BigDecimal calcularPrecoFinal() {
        BigDecimal precoFinal = preco; // Altera o preço a depender do serviço solicitado

        if (incluiBanho) {
            precoFinal = precoFinal.add(new BigDecimal("35.00"));
        }

        if (incluiTosa) {
            precoFinal = precoFinal.add(new BigDecimal("40.00"));
        }

        return precoFinal;
    }
        @Override
    public String getDescricaoDetalhada() {
        // Usa um StringJoiner para juntar na string quando forem selecionados os dois serviços estéticos
        StringJoiner descricao = new StringJoiner(" e ");
        if (incluiBanho) {
            descricao.add("Banho");
        }
        if (incluiTosa) {
            descricao.add("Tosa");
        }
        // Se não tiver banho ou tosa retorna o nome base.
        return descricao.length() > 0 ? descricao.toString() : this.nome;
    }


 //Métodos Get e Set para o usuário alterar os atributos respeitando o encapsulamento
    public void setIncluiBanho(boolean incluiBanho){
        this.incluiBanho = incluiBanho;
    }

    public boolean getIncluiBanho(){
        return this.incluiBanho;
    }

    public void setIncluiTosa(boolean incluiTosa){
        this.incluiTosa = incluiTosa;
    }

    public boolean getIncluiTosa(){
        return this.incluiTosa;
    }
}
