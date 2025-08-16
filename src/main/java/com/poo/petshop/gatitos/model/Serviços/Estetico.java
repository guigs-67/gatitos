package com.poo.petshop.gatitos.model.Serviços;
import java.math.BigDecimal;

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
