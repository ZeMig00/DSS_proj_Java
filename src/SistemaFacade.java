import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaFacade implements Serializable, ISistemaFacade {
    private int id_atual = 0;
    private int max_expressos;
    private Map<String,Cliente> clientes;
    private Map<Integer,Equipamento> equipamentos;
    private Map<String,TipoDeExpresso> tabelas_expresso;
    private Map<String,Trabalhador> trabalhadores;
    private List<EquipamentoEntregue> equipamentosEntregues;
    private List<OrcamentoRecusado> arquivoOrcamento;

    SistemaFacade(int max){
        this.clientes = new HashMap<>();
        this.equipamentos = new HashMap<>();
        this.tabelas_expresso = new HashMap<>();
        this.trabalhadores = new HashMap<>();
        this.equipamentosEntregues = new ArrayList<>();
        this.arquivoOrcamento = new ArrayList<>();
        this.max_expressos = max;
    }

    public String isTrabalhador(String username, String password){
        Trabalhador trabalhador = this.trabalhadores.get(username);
        if(trabalhador!=null && trabalhador.verifyPassword(password)){
            return trabalhador.getClass().getSimpleName();
        } else return null;
    }

    public boolean clienteNovo(String nif){
        return this.clientes.get(nif)==null;
    }

    public void registarCliente(String nif, String email, String numero){
        this.clientes.put(nif,new Cliente(nif,email,numero));
    }

    public boolean registarEquipamento(String funcionario,String descricao, String nif,Servico serv) {
        Equipamento equip = new Equipamento(nif,descricao,serv,this.id_atual);
        Cliente cliente = this.clientes.get(nif);
        Funcionario f = (Funcionario) this.trabalhadores.get(funcionario);

        if(cliente!=null && f!=null) {
            cliente.addEquipamento(equip);
            this.clientes.put(nif, cliente);

            this.equipamentos.put(this.id_atual++, equip); // adiciona equip e incrementa id_atual

            f.incrementNumRecepcoes(); // incrementar num recepcoes
            this.trabalhadores.put(funcionario, f);
        }

        return cliente!=null && f!=null;
    }


    public void registarEntrega(String funcionario,int idEquip,String nif) {
        Equipamento equip = this.equipamentos.remove(idEquip); // remove do HashMap
        Cliente cliente = this.clientes.get(nif);
        Funcionario f = (Funcionario) this.trabalhadores.get(funcionario);

        cliente.removeEquipamento(equip); // remove do cliente
        this.clientes.put(nif,cliente);

        this.equipamentosEntregues.add(new EquipamentoEntregue(equip)); // regista nos equipantosEntregues

        f.incrementNumEntregas(); // incrementar num entregas
        this.trabalhadores.put(funcionario,f);
    }


    public List<Trabalhador> getListaTecnicos() {
        return this.trabalhadores.values().stream().filter(s -> s instanceof Tecnico).collect(Collectors.toList());
    }


    public List<Trabalhador> getListaFuncionarios() {
        return this.trabalhadores.values().stream().filter(s -> s instanceof Funcionario).collect(Collectors.toList());
    }


    public List<Plano> getListaIntervencoesPlanos(String idTecnico) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        if(tecnico!=null){
            return tecnico.getPlanos();
        }
        return null;
    }

    public List<TipoDeExpresso> getListaIntervencoesExpressos(String idTecnico) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        if(tecnico!=null){
            return tecnico.getExpressos();
        }
        return null;
    }

    public List<Equipamento> getListaEquipReparar() {
        return this.equipamentos.values().stream().filter(Equipamento::porReparar).sorted().collect(Collectors.toList());
    }

    public List<Equipamento> getListaPedidosOrcamento() {
        return this.equipamentos.values().stream().filter(Equipamento::porValidar)
                .sorted(Comparator.comparing(Equipamento::getRegisterDate)).collect(Collectors.toList());
    }

    public List<TipoDeExpresso> getServicosExpresso(){
        return new ArrayList<>(this.tabelas_expresso.values());
    }


    public double calcularOrcamento(int idEquipamento) {
        return this.equipamentos.get(idEquipamento).calculaPreco();
    }


    public boolean registarOrcamento(String idTrabalhador, int idEquipamento, Plano plano) {
        Equipamento equip = this.equipamentos.get(idEquipamento);
        Trabalhador trabalhador = this.trabalhadores.get(idTrabalhador);

        if(equip!=null && equip.getServico() instanceof Geral && trabalhador instanceof Tecnico){
            equip.setPlano(plano);
            this.equipamentos.put(idEquipamento,equip);
        }

        return equip!=null && equip.getServico() instanceof Geral && trabalhador instanceof Tecnico;
    }

    public void introduzirPlanoIncompleto(String idTrabalhador, int idEquipamento, Plano plano){
        Equipamento equip = this.equipamentos.get(idEquipamento);
        Trabalhador trabalhador = this.trabalhadores.get(idTrabalhador);

        if(equip!=null && equip.getServico() instanceof Geral && trabalhador instanceof Tecnico tecnico){
            equip.setPlanoIncompleto(plano);
            this.equipamentos.put(idEquipamento,equip);
        }
    }


    public void arquivarOrcamentos() { // remove todos os que passaram do prazo de 30 dias
        for (Equipamento e : this.equipamentos.values()){
            if(e.expirado()){
                arquivarOrcamento(e.getId(),"Fora de Prazo");
            }
        }
    }


    public void concluirReparacao(String tecnico, int id) {
        Equipamento equip = this.equipamentos.get(id);
        equip.concluirReparacao();
        this.equipamentos.put(id,equip);

        Tecnico t = (Tecnico) this.trabalhadores.get(tecnico);
        if(t!=null) {
            if (equip.getServico() instanceof Geral){
                t.addPlano(equip.getPlano());
                t.incrementReparacoesGeral();
            }
            else if(equip.getServico() instanceof Expresso expresso){
                t.addExpresso(expresso.getExpresso());
                t.incrementReparacoesExpresso();
            }

            this.trabalhadores.put(tecnico, t);
        }
    }


    public void registarPassoFeito(int idEquipamento,int duracao) {
        Equipamento equip = this.equipamentos.get(idEquipamento);

        if(equip!=null && equip.getServico() instanceof Geral geral && geral.getOrcamento().planoFeito()){
            equip.passoFeito(duracao);
            this.equipamentos.put(idEquipamento,equip);
        }
    }

    public int verificarPassoAtual(int idEquipamento) {
        Equipamento equip = this.equipamentos.get(idEquipamento);
        if (equip!=null){
            return equip.verificaPassoAtual();
        }
        return -1;
    }

    public void registarFuncionario(String id, String pass){
        Trabalhador f = new Funcionario(id,pass);
        this.trabalhadores.put(id,f);
    }

    public void registarTipoDeExpresso(String nome, double preco){
        TipoDeExpresso t = new TipoDeExpresso(nome,preco);
        this.tabelas_expresso.put(nome,t);
    }

    public void registarTecnico(String id, String pass){
        Trabalhador f = new Tecnico(id,pass);
        this.trabalhadores.put(id,f);
    }

    public List<Equipamento> equipReparadosCliente(String nif){
        Cliente c = this.clientes.get(nif);
        if (c!=null) return c.equipamentosCliente().stream().filter(Equipamento::getReparado).collect(Collectors.toList());
        else return null;
    }

    public List<Equipamento> pedidosCliente(String nif){
        Cliente c = this.clientes.get(nif);
        if (c!=null) return c.equipamentosCliente().stream().filter(Equipamento::recusado)
                .collect(Collectors.toList());
        else return null;
    }

    public String getContactoEmail(String nif){
        return this.clientes.get(nif).getEmail();
    }

    public String getContactoNumero(String nif){
        return this.clientes.get(nif).getNumero();
    }

    public void aceitarOrcamento(int id){
        Equipamento equip = this.equipamentos.get(id);
        equip.aceitarOrcamento();
    }

    public void arquivarOrcamento(int idEquip,String motivo) {
        Equipamento equip = this.equipamentos.get(idEquip);
        equip.concluirReparacao();
        this.equipamentos.put(idEquip, equip);

        this.arquivoOrcamento.add(new OrcamentoRecusado(equip, motivo));
    }

    public boolean recusarExpresso(){
        return this.max_expressos <= this.equipamentos.values().stream().filter(e -> e.getServico() instanceof Expresso && e.porReparar()).count();
    }
}
