package com.poo.petshop.gatitos.model.NotaFiscal;
import com.poo.petshop.gatitos.model.Cliente.Cliente;
import com.poo.petshop.gatitos.model.Serviços.Servicos;
import com.poo.petshop.gatitos.model.ItemNota.ItemNota;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotaFiscal {

    private int id;
    private Cliente cliente;
    private final LocalDateTime dataHora;
    private final List<Servicos> servicos;
    private List<ItemNota> itens;
    private BigDecimal totalFinal;

    //Construtor
    public NotaFiscal(Cliente cliente) {
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now();
        this.servicos = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.totalFinal = BigDecimal.ZERO;
    }
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }
    public List<ItemNota> getItens() {
        return this.itens;
    }

    public BigDecimal getTotalFinal() {
        return this.totalFinal;
    }
    //Adiciona um dos serviços à nota
    public void adicionarServico(Servicos servico) {
        this.servicos.add(servico);
        this.calcularItensEtotal(); // Recalcula tudo sempre que algo novo é adicionado
    }

    //Metodo de impressão
    public void imprimir() {
        //Formatador para estilo de data utilizada no nosso país.
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        System.out.println("\n========================================");
        System.out.println("          NOTA FISCAL DO PETSHOP");
        System.out.println("========================================");
        System.out.println("Nota ID: " + this.id); 
        System.out.println("Cliente: " + this.cliente.getNome() + " | CPF: " + this.cliente.getCPF());
        System.out.println("Emitida em: " + this.dataHora.format(formatador));
        System.out.println("----------------------------------------");

        if (this.itens.isEmpty()) {
            System.out.println("Nenhum serviço foi adicionado...");
        } else {
            for (ItemNota item : this.itens) {
                System.out.println(item);
            }
        }

        System.out.println("----------------------------------------");
        System.out.printf("VALOR TOTAL: R$ %.2f\n", this.totalFinal);
        System.out.println("========================================");
    }

    //Metodo para calcular subtotal da linha da nota fiscal e total final.
    private void calcularItensEtotal() {
        //Mapea a Lista de serviços e agrupa os servicos pela descricao
        Map<String, List<Servicos>> servicosAgrupados = this.servicos.stream()
                .collect(Collectors.groupingBy(Servicos::getDescricaoDetalhada));
        this.itens.clear();
        //Agora para montar a linha da Nota Fiscal:
        for (Map.Entry<String, List<Servicos>> entry : servicosAgrupados.entrySet()) {
            //Pega a descrição
            String descricao = entry.getKey();
            //Verifica o grupo com essa descrição
            List<Servicos> grupo = entry.getValue();
            //Pega a quantidade de vezes que esse grupo de serviço aparece
            int quantidade = grupo.size();
            //Utiliza o método calcularPrecoFinal para pegar o total da linha da nota
            BigDecimal valorUnitario = grupo.get(0).calcularPrecoFinal();
            //Adiciona na Lista de linhas da NotaFiscal
            this.itens.add(new ItemNota(descricao, quantidade, valorUnitario));
        }
        //Calcula o final total
        this.totalFinal = this.itens.stream()
                .map(ItemNota::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
