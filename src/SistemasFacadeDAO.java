import java.io.*;

public class SistemasFacadeDAO {

    public static void storeEstado(ISistemaFacade sistemaFacade,String nomeFicheiro) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(sistemaFacade);

        oos.flush();
        oos.close();

    }

    public static SistemaFacade loadEstado(String fich) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(fich);
        ObjectInputStream ois = new ObjectInputStream(fis);

        return (SistemaFacade) ois.readObject();
    }

}
