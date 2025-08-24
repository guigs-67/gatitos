package com.poo.petshop.gatitos.model.Serviços;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Nosso padrão de projeto escolhido -COMPOSITE-:
//Composite é usado para compor objetos em estruturas de arvores e permite que objetos individuais e hierarquia de objetos sejam tratados de maneira uniforme.
//Nossa classe composite Pacote de Serviços é um classe filha da superclasse Servicos que vai utilizar dos seus metodos e além disso poderá gerenciar os outros filhos da classe servico (saúde e estética)
public class PacoteDeServicos extends Servicos {

    //Cria uma lista de serviços
    private final List<Servicos> servicosDoPacote = new ArrayList<>();

    //Construtor
    public PacoteDeServicos(String nomeDoPacote) {
        // Preço base será 0 e o preço final será a soma dos pacotes.
        super(nomeDoPacote, BigDecimal.ZERO);
    }

    //Metodo para adicionar filhos de serviço
    public void adicionar(Servicos servico) {
        this.servicosDoPacote.add(servico);
    }

    //Método para remover -- - - - - -- 
    public void remover(Servicos servico) {
        this.servicosDoPacote.remove(servico);
    }

    //Sobrescrita do método abstrato calcularPrecoFinal
    @Override
    public BigDecimal calcularPrecoFinal() {
        BigDecimal totalPacote = BigDecimal.ZERO;
        // Para cada serviço dentro da lista, eu verifico o valor final daquele serviço utilizando o calcular preço final das suas proprias classes.
        for (Servicos servico : servicosDoPacote) {
            totalPacote = totalPacote.add(servico.calcularPrecoFinal());
        }
        //retorno o valor final
        return totalPacote;
    }

    //Sobrescrita do metodo  abstrato getDescriçãoDetalhada
    @Override
    public String getDescricaoDetalhada() {
        //String builder para que minha lista de Strings não seja recopiada a cada modificação o que deixaria o desempenho lento de forma escalonável
        StringBuilder descricao = new StringBuilder();
        //Imprime o nome do Pacote e os colchetes que serão preenchidos posteriormente.
        descricao.append("Pacote '").append(this.nome).append("': [");
        
        //Crio uma nova lista
        List<String> descricoesServicosPacotes = new ArrayList<>();
        //Preencho a lista criada com a descrição de cada serviço utilizando o metodo de descrição das suas proprias classes.
        for (Servicos servico : servicosDoPacote) {
            descricoesServicosPacotes.add(servico.getDescricaoDetalhada());
        }
        
        //Atribuo virgula a cada serviço para ficar organizado.
        descricao.append(String.join(", ", descricoesServicosPacotes));
        //Fecho o String Builder
        descricao.append("]");
        return descricao.toString();
    }
}