package com.poo.petshop.gatitos.model.Serviços;
import java.math.BigDecimal;
import java.util.StringJoiner;

public class Saude extends Servicos {

    private boolean consultaEspecialista;
    private boolean vacina;

  //construtor
    public Saude(String nome, BigDecimal preco, boolean consultaEspecialista, boolean vacina){
       super( nome, preco);
       this.consultaEspecialista = consultaEspecialista;
       this.vacina = vacina;
    }

    public BigDecimal calcularPrecoFinal(){ // Altera o preço a depender do serviço solicitado
          BigDecimal precoFinal = preco;
        if (consultaEspecialista) {
            precoFinal = precoFinal.add(new BigDecimal("100.00"));
        }

        if (vacina) {
            precoFinal = precoFinal.add(new BigDecimal("20.00"));
        }

        return precoFinal;
    }

    @Override
    public String getDescricaoDetalhada() {
        // Usa um StringJoiner para juntar na string quando forem selecionados os dois serviços de saúde.
        StringJoiner descricao = new StringJoiner(" e ");
        if (consultaEspecialista) {
            descricao.add("Consulta com Especialista");
        }
        if (vacina) {
            descricao.add("Vacina");
        }
        // Se não tiver consulta ou vacina retorna o nome base do serviço
        return descricao.length() > 0 ? descricao.toString() : this.nome;
    }

     //Métodos Get e Set para o usuário alterar os atributos respeitando o encapsulamento
    public void setConsultaEspecialista(boolean consultaEspecialista){
        this.consultaEspecialista = consultaEspecialista;
    }

    public boolean getConsultaEspecialista(){
        return this.consultaEspecialista;
    }

    public void setVacina(boolean vacina){
        this.vacina = vacina;
    }

    public boolean getVacina(){
        return this.vacina;
    }
}
