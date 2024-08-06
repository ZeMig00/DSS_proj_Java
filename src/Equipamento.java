import java.io.Serializable;
import java.time.LocalDate;

public class Equipamento implements Comparable, Serializable {
    private String descricao;
    private boolean reparado;
    private Servico servico;
    private int id;
    private String nif;
    private LocalDate registerDate;

    Equipamento(String nif,String descricao, Servico servico, int id) {
        this.nif = nif;
        this.descricao = descricao;
        this.reparado = false;
        this.servico = servico;
        this.id = id;
        this.registerDate = LocalDate.now();
    }

    Equipamento(Equipamento eq) {
        this.descricao = eq.descricao;
        this.reparado = eq.reparado;
        this.servico = eq.servico;
        this.id = eq.id;
        this.nif = eq.nif;
        this.registerDate = eq.registerDate;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public int getId() {
        return this.id;
    }

    public String getNif() {
        return this.nif;
    }

    public Servico getServico() {
        return this.servico;
    }

    public LocalDate getRegisterDate() {
        return this.registerDate;
    }

    public boolean getReparado() {
        return this.reparado;
    }

    public void setPlano(Plano plano) {
        if (this.servico instanceof Geral geral) {
            geral.setPlano(plano);
            this.servico = geral;
        }
    }

    public void setPlanoIncompleto(Plano plano) {
        if (this.servico instanceof Geral geral) {
            geral.setPlanoIncompleto(plano);
            this.servico = geral;
        }
    }

    public Plano getPlano(){
        if (this.servico instanceof Geral geral) {
            return geral.getPlano();
        }
        else return null;
    }

    public void concluirReparacao() {
        this.reparado = true;
        if (this.servico instanceof Geral geral) geral.getOrcamento().setEstado();
    }

    public double calculaPreco() {
        return servico.calculaPreco();
    }

    public void passoFeito(int duracao) {
        if (this.servico instanceof Geral geral) {
            geral.passoFeito(duracao);
            this.servico = geral;
        }
    }

    public boolean porReparar() {
        return servico.estaValidado() && !reparado;
    }

    public boolean porValidar() {
        return !servico.estaValidado();
    }

    public int verificaPassoAtual() {
        if (this.servico instanceof Geral geral) return geral.verificaPassoAtual();
        else return -1;
    }

    private int planSize() {
        if(this.getPlano()!=null) return this.getPlano().size();
        else return 0;
    }

    public String toString() {
        String string = "Id: " + this.id + "; Descrição: " + this.descricao
                + "; Serviço: " + this.servico.getClass().getSimpleName()
                + "; Preco: " + this.calculaPreco() + "; Data : " + this.registerDate;
        if (this.servico instanceof Geral) {
            string = string + "; Passo Atual: " + this.verificaPassoAtual() + "/" + this.planSize();
        }
        return string;
    }

    public Equipamento clone() {
        return new Equipamento(this);
    }

    @Override
    public int compareTo(Object o) {
        Equipamento outro = (Equipamento) o;
        if(this.servico instanceof Expresso) return -1;
        if(this.servico instanceof Geral && outro.servico instanceof Expresso) return 1;
        if(this.servico instanceof Geral && outro.servico instanceof Geral) return this.registerDate.compareTo(outro.registerDate);
        return 0;
    }

    public boolean recusado() {
        if (this.servico instanceof Geral geral) return geral.recusado();
        else return false;
    }

    public boolean expirado() {
        return this.servico.expirado();
    }

    public void aceitarOrcamento() {
        if (this.getServico() instanceof Geral geral){
            geral.aceitarOrcamento();
        }
    }
}
