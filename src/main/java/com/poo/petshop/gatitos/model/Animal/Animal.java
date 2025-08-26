package com.poo.petshop.gatitos.model.Animal;

public class Animal {
 
    private String nome;
    private double peso;
    private String porte;
    private String especie;
    private String raca;
    private String sexo;
    private String cpfDono; // Campo para vincular o animal ao CPF do cliente

    // Construtor vazio para o Spring criar o objeto automaticamente
    public Animal() {
    }

    public Animal(String nome, double peso, String porte, String especie, String raca, String sexo, String cpfDono){
        this.nome = nome;
        this.peso = peso;
        this.porte = porte;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
        this.cpfDono = cpfDono;
    }

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

    public double getPeso() {
        return this.peso;
    }
    
    public void setPeso(double peso) {
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