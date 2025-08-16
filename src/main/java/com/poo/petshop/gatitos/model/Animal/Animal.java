package com.poo.petshop.gatitos.model.Animal;

public class Animal{
 
    private String nome;
    private int peso;
    private String porte;
    private String especie;
    private String raca;
    private String sexo;

    public Animal(String nome, int peso, String porte, String especie, String raca, String sexo){
        this.nome = nome;
        this.peso = peso;
        this.porte = porte;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
    }
     //Métodos Get e Set para o usuário alterar os atributos respeitando o encapsulamento

     public void setNome(String nome){
        this.nome = nome;
     }

     public String getNome(){
        return this.nome;
     }

     public void setPeso(int peso){
        this.peso = peso;
     }

     public int getPeso(){
        return this.peso;
     }

     public void setPorte(String porte){
        this.porte = porte;
     }

     public String getPorte(){
        return this.porte;
     }

    public void setEspecie(String especie){
        this.especie = especie;
    }

    public String getEspecie(){
        return this.especie;
    }

    public void setRaca(String raca){
        this.raca = raca;
    }

    public String getRaca(){
        return this.raca;
    }

    public void setSexo(String sexo){
        this.sexo = sexo;
    }

    public String getSexo(){
        return this.sexo;
    }
}