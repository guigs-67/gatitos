package com.poo.petshop.gatitos.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import com.poo.petshop.gatitos.model.Animal.Animal;

public class Cliente {
    private String cpf;
    private String endereco;
    private String telefone;
    private String nome;
    private List<Animal> animais; //um cliente pode ter varios animais

    public Cliente(String cpf, String endereco, String telefone, String nome){
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.nome = nome;
        this.animais = new ArrayList<>(); //Lista com animais que inicialmente começa vazia
    }

    public void adicionarAnimal(Animal animal){ //método pega um animal que já foi cadastrado e adiciona ele a lista de animais do cliente
        this.animais.add(animal);
    }
    //Métodos Get e Set para o usuário alterar os atributos respeitando o encapsulamento
    public void setCPF(String cpf){
        this.cpf = cpf;
    }

    public String getCPF(){
        return this.cpf;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String getEndereco(){
        return this.endereco;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getTelefone(){
        return this.telefone;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public List<Animal> getAnimais(){ //devolve todos os animais do cliente
        return this.animais;
    }
}