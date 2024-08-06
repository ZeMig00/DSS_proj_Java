import java.io.Serializable;

public abstract class Servico implements Comparable, Serializable {

    public abstract double calculaPreco();

    public abstract boolean estaValidado();

    public abstract boolean expirado();
}
