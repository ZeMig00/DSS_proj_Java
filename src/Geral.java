public class Geral extends Servico{
    private Orcamento orcamento;

    Geral(){
        this.orcamento = new Orcamento();
    }

    public Orcamento getOrcamento(){
        return this.orcamento;
    }

    public void setPlano(Plano plano){
        this.orcamento.setPlano(plano);
    }

    public void aceitarOrcamento(){
        this.orcamento.setAceite(true);
    }

    public void setPlanoIncompleto(Plano plano){
        this.orcamento.setPlanoIncompleto(plano);
    }

    public int verificaPassoAtual(){
        if (this.orcamento.getPlano()!=null) return this.orcamento.verificaPasso();
        else return 0;
    }

    public boolean expirado() {
        return this.getOrcamento().expirado();
    }

    public Plano getPlano(){
        return this.orcamento.getPlano();
    }


    public double calculaPreco() {
        return this.orcamento.calculaOrcamento();
    }

    public boolean estaValidado() {
        return this.orcamento.planoFeito();
    }

    public boolean recusado() {
        return this.orcamento.recusado();
    }

    public void passoFeito(int duracao) {
        this.orcamento.passoFeito(duracao);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
