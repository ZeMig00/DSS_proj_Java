public class Expresso extends Servico{
    private TipoDeExpresso expresso;

    Expresso(TipoDeExpresso expresso){
        this.expresso = expresso;
    }

    Expresso(String tipo, double preco){ this.expresso = new TipoDeExpresso(tipo,preco);}

    public TipoDeExpresso getExpresso(){
        return this.expresso;
    }

    public double calculaPreco() {
        return this.expresso.getPreco();
    }

    public int compareTo(Object o) {
        return 0;
    }
    
    public boolean estaValidado() {
        return true;
    }

    public boolean expirado() {
        return false;
    }

    public String tipoDeExpresso(){
        return this.expresso.getNome();
    }
}
