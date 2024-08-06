public class OrcamentoRecusado {
    private double preco;
    private String motivo;

    public OrcamentoRecusado(){
        this.preco = 0;
        this.motivo = "";
    }

    public OrcamentoRecusado(Equipamento eq, String motivo){
        this.preco = eq.calculaPreco();
        this.motivo = motivo;
    }
}
