package com.poo.petshop.gatitos.model.Animal;

public class Animal {
 
    private String nome;
    private int peso;
    private String porte;
    private String especie;
    private String raca;
    private String sexo;
    private String cpfDono; // Campo para vincular o animal ao CPF do cliente

    // Construtor vazio para o Spring
    public Animal() {
    }

    public Animal(String nome, int peso, String porte, String especie, String raca, String sexo, String cpfDono){
        this.nome = nome;
        this.peso = peso;
        this.porte = porte;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
        this.cpfDono = cpfDono;
    }

    // Getters e Setters para todos os campos, incluindo o novo de cpfDono

    public String getCpfDono() {
        return cpfDono;
    }

    public void setCpfDono(String cpfDono) {
        this.cpfDono = cpfDono;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPeso() {
        return this.peso;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getPorte() {
        return this.porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getEspecie() {
        return this.especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return this.raca;
    }
    
    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}