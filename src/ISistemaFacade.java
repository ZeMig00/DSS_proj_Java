import java.util.List;

public interface ISistemaFacade {

    String isTrabalhador(String username, String password);

    boolean registarEquipamento(String funcionario, String descricao, String nif, Servico serv);

    void registarEntrega(String funcionario, int id, String nif);

    List<Trabalhador> getListaTecnicos();

    List<Trabalhador> getListaFuncionarios();

    List<Plano> getListaIntervencoesPlanos(String idTecnico);

    List<TipoDeExpresso> getListaIntervencoesExpressos(String idTecnico);

    int verificarPassoAtual(int idEquipamento);

    double calcularOrcamento(int idEquipamento);

    boolean registarOrcamento(String idTrabalhador, int idEquipamento, Plano plano);

    void arquivarOrcamentos();

    List<Equipamento> getListaEquipReparar();

    void concluirReparacao(String tecnico, int id);

    void registarPassoFeito(int idEquipamento,int duracao);

    void registarFuncionario(String id, String pass);

    void registarTecnico(String id, String pass);

    List<TipoDeExpresso> getServicosExpresso();

    List<Equipamento> equipReparadosCliente(String nif);

    String getContactoEmail(String nif);

    String getContactoNumero(String nif);

    void aceitarOrcamento(int id);

    List<Equipamento> getListaPedidosOrcamento();

    List<Equipamento> pedidosCliente(String nif);

    void introduzirPlanoIncompleto(String idTrabalhador, int idEquipamento, Plano plano);

    boolean clienteNovo(String nif);

    void registarCliente(String nif, String email, String numero);

    void registarTipoDeExpresso(String nome, double preco);

    void arquivarOrcamento(int idEquip, String motivo);

    boolean recusarExpresso();
}
