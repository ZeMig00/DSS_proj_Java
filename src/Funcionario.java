
public class Funcionario extends Trabalhador {
    private int numRecepcoes = 0;
    private int numEntregas = 0;

    Funcionario(String identificador,String pass){
        super(identificador,pass);
    }

    public int getNumEntregas() {
        return numEntregas;
    }

    public int getNumRecepcoes() {
        return numRecepcoes;
    }

    public void incrementNumEntregas() {
        this.numEntregas++;
    }

    public void incrementNumRecepcoes() {
        this.numRecepcoes++;
    }

    public String toString() {
        return "Nome: "+ this.getIdentificador()
                + "; Nº Recepcoes: " + numRecepcoes
                + "; Nº Entregas: " + numEntregas;
    }
}
