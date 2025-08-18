package com.poo.petshop.gatitos.model.ItemNota;
import java.math.BigDecimal;

public class ItemNota {

    
    private final String descricao;
    private final int quantidade;
    private final BigDecimal valorUnitario;
    private final BigDecimal valorTotal;

    public ItemNota(String descricao, int quantidade, BigDecimal valorUnitario) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        // Para calcular o valor da linha (valor do serviço * quantidade de vezes)
        this.valorTotal = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    //To String formato para a descrição da Nota Fiscal, a classe itemnota reperesenta cada item e quantidade de forma separada junto ao seu valor da unidade e soma total daquela linha
    @Override
    public String toString() {
        return String.format("%dx %s (Unit: R$ %.2f) - Total Linha: R$ %.2f", quantidade, descricao, valorUnitario, valorTotal);
    }
    
}
