import java.io.Serializable;

public class EquipamentoEntregue implements Serializable {
    private int id;
    private String descricao;
    private double pagamento;

    EquipamentoEntregue(Equipamento equip){
        this.id = equip.getId();
        this.descricao = equip.getDescricao();
        if (equip.recusado()) this.pagamento = 0;
        else this.pagamento = equip.calculaPreco();
    }
}
