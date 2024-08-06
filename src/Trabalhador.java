import java.io.Serializable;
import java.util.Objects;

public abstract class Trabalhador implements Serializable {
    private String identificador;
    private String pass;

    Trabalhador(String identificador,String pass){
        this.identificador = identificador;
        this.pass = pass;
    }

    public String getIdentificador(){
        return this.identificador;
    }

    public String getPass(){
        return this.pass;
    }

    public boolean verifyPassword(String pass){
        return Objects.equals(this.pass, pass);
    }

    public abstract String toString();
}
