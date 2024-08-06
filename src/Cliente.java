import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cliente implements Serializable {
    private String nif;
    private String email;
    private String num;
    private List<Equipamento> eq;

    Cliente(String nif,String email,String num){
        this.nif = nif;
        this.email = email;
        this.num = num;
        this.eq = new ArrayList<>();
    }

    public String getNif(){
        return this.nif;
    }

    public void setContacto(String id){
        this.num = id;
    }

    public String getEmail(){
        return this.email;
    }

    public List<Equipamento> equipamentosCliente(){
        return this.eq.stream().map(Equipamento::clone).collect(Collectors.toList());
    }

    public void addEquipamento(Equipamento equip){
        this.eq.add(equip);
    }

    public void removeEquipamento(Equipamento equip){
        this.eq.remove(equip);
    }

    public String getNumero() {
        return this.num;
    }
}
