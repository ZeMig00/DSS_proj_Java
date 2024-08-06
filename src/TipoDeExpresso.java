import java.io.Serializable;

public class TipoDeExpresso implements Serializable {
    private String nome;
    private double preco;

    TipoDeExpresso(String nome,double preco){
        this.nome = nome;
        this.preco = preco;
    }

    TipoDeExpresso(TipoDeExpresso ts){
        this.nome = ts.nome;
        this.preco = ts.preco;
    }

    public String getNome(){
        return this.nome;
    }

    public double getPreco(){
        return this.preco;
    }

    public TipoDeExpresso clone(){
        return new TipoDeExpresso(this);
    }

    public String toString() {
        return "Servico: " + nome + ';' +
                " Preco: " + preco;
    }
}