import java.util.ArrayList;
import java.util.List;

public class Tecnico extends Trabalhador {
    private List<Plano> planos;
    private List<TipoDeExpresso> expressos;
    private int numReparacoesGeral = 0;
    private int numReparacoesExpresso = 0;

    Tecnico(String identificador,String pass){
        super(identificador,pass);
        this.planos = new ArrayList<>();
        this.expressos = new ArrayList<>();
    }

    Tecnico(Tecnico t){
        super(t.getIdentificador(),t.getPass());
        this.planos = t.planos;
        this.expressos = t.expressos;
    }

    public List<Plano> getPlanos(){
        return new ArrayList<>(this.planos);
    }

    public List<TipoDeExpresso> getExpressos(){
        return new ArrayList<>(this.expressos);
    }

    public void addPlano(Plano plano){
        this.planos.add(plano);
    }

    public void addExpresso(TipoDeExpresso expresso){
        this.expressos.add(expresso);
    }

    public int GetNumReparacoesExpresso(){
        return this.numReparacoesExpresso;
    }

    public int GetNumReparacoesGeral(){
        return this.numReparacoesGeral;
    }

    public void incrementReparacoesExpresso(){
        this.numReparacoesExpresso++;
    }

    public void incrementReparacoesGeral(){
        this.numReparacoesGeral++;
    }

    public double duracaoMedia(){
        if (this.planos.size()>0) return this.planos.stream().mapToDouble(Plano::calculaDuracao).sum()/this.planos.size();
        else return 0;
    }

    public double duracaoMediaDesvio(){
        if (this.planos.size()>0) return this.planos.stream().mapToDouble(Plano::calculaDuracaoDesvio).sum()/this.planos.size();
        else return 0;
    }

    public String toString() {
        return "Nome: "+ this.getIdentificador()
                + "; Nº ReparacoesGeral: " + this.numReparacoesGeral
                + "; Nº ReparacoesExpresso: " + this.numReparacoesExpresso
                + "; Duracao Media: " + this.duracaoMedia()
                + "; Desvio Medio: " + this.duracaoMediaDesvio();
    }
}
