import java.io.Serializable;

public class Passo implements Serializable {
    private String descricao;
    private double preco;
    private int duracaoProgramada;
    private int duracaoReal;

    Passo(String descricao,double preco,int duracao){
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoProgramada = duracao;
        this.duracaoReal = duracao;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public double getPreco(){
        return this.preco;
    }

    public int getDuracao(){
        return this.duracaoProgramada;
    }

    public int getDesvio(){
        return Math.abs(this.duracaoProgramada-this.duracaoReal);
    }

    public void setDuracaoReal(int duracaoReal){
        this.duracaoReal = duracaoReal;
    }

    public String toString(){
        return "Descricao: "
                + this.descricao + "\n"
                + "Preco: " + this.preco
                + "; Duracao Programada: " + this.duracaoProgramada
                + "; Duracao Real: " + this.duracaoReal;
    }
}
