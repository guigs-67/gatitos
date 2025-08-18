package com.poo.petshop.gatitos.model.Serviços;
import java.math.BigDecimal;

public abstract class Servicos {
    //protected para as classes que herdarão dessas possam acessar e alterar esses dados diretamente
  protected String nome;
  protected BigDecimal preco;

  //construtor
  public Servicos(String nome, BigDecimal preco){
    this.nome = nome;
    this.preco = preco;
  }
   
  //Métodos Get e Set para o usuário alterar os atributos respeitando o encapsulamento
  public String getNome(){
    return this.nome;
  }

  public void setNome(String nome){
    this.nome = nome;
  }

  public BigDecimal getPreco(){
    return this.preco;
  }

  public void setPreco(BigDecimal preco){
    this.preco = preco;
  }

  //Método abstrato que vai variar conforme o serviço solicitado
  public abstract BigDecimal calcularPrecoFinal();

  //Método abstrato que vai criar a descrição do serviço que usarei na "impressão da nota fiscal"
  public abstract String getDescricaoDetalhada();
}
