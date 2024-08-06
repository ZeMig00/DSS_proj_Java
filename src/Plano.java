import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plano implements Serializable {
    private int passo_atual = 0;
    private List<Passo> passos;

    Plano(){
        this.passos = new ArrayList<>();
    }

    Plano(String expresso,double preco,int duracao){
        this.passos = new ArrayList<>();
        this.passos.add(new Passo(expresso,preco,duracao));
    }

    public int getPassoAtual(){
        return this.passo_atual;
    }

    public void addPasso(Passo passo){
        this.passos.add(passo);
    }
    
    public void addPasso(String descricao,String tempo,String preco){
        Passo p = new Passo(descricao,Integer.parseInt(preco),Integer.parseInt(tempo));
        this.addPasso(p);
    }

    public boolean isLastPasso(){
        return this.passos.size()==this.passo_atual;
    }

    public double calculaOrcamento(){
        return this.passos.stream().mapToDouble(Passo::getPreco).sum();
    }

    public int calculaDuracao(){
        return this.passos.stream().mapToInt(Passo::getDuracao).sum();
    }

    public double calculaDuracaoDesvio() {
        return this.passos.stream().mapToInt(Passo::getDesvio).sum();
    }

    public void registarPassoFeito(int duracao) {
        if(!this.isLastPasso()){
            Passo p = this.passos.get(passo_atual);
            p.setDuracaoReal(duracao);
            this.passo_atual++;
        }
    }

    public String toString(){
        int counter=0;
        StringBuilder sb = new StringBuilder();
        sb.append("Plano: \n");
        for (Passo p : this.passos) {
            sb.append(counter++).append(") ").append(p).append("\n");
        }
        sb.append("Passo Atual: ").append(this.passo_atual).append('\n');
        return sb.toString();
    }

    public int size() {
        return this.passos.size();
    }

}
