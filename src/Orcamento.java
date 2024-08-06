import java.io.Serializable;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Orcamento implements Serializable {
    private boolean estado; // se ja tem plano ou nao
    private boolean aceite;
    private Plano plano;
    private LocalDate planDate;

    Orcamento(){
        this.estado = false;
        this.plano = null;
        this.planDate = null;
        this.aceite = false;
    }

    public LocalDate getRegisterDate(){
        return this.planDate;
    }

    public boolean planoFeito(){
        return this.estado;
    }

    public boolean orcamentoAceite(){
        return this.aceite;
    }

    public Plano getPlano(){
        return this.plano;
    }

    public boolean expirado(){
        if (this.estado) {
            return DAYS.between(this.planDate, LocalDate.now()) > 30;
        }
        return false;
    }

    public boolean recusado(){
        return !this.aceite && this.plano!=null;
    }

    public void setPlano(Plano plano){
        this.estado = true;
        this.plano = plano;
        this.planDate = LocalDate.now();
    }

    public void setPlanoIncompleto(Plano plano){
        this.plano = plano;
    }

    public void setAceite(boolean aceite){
        this.aceite = aceite;
    }

    public void setEstado(){
        this.estado = true;
    }

    public double calculaOrcamento(){
        if (this.plano!=null) return this.plano.calculaOrcamento();
        else return 0;
    }

    public void passoFeito(int duracao) {
        this.plano.registarPassoFeito(duracao);
    }

    public int verificaPasso() {
        return this.plano.getPassoAtual();
    }
}
