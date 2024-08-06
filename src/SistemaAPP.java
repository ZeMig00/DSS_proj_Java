import java.io.*;
import java.util.List;

public class SistemaAPP {

    private final int MAX_EXPRESS = 2;

    private ISistemaFacade sf = new SistemaFacade(MAX_EXPRESS);

    private Menu menuPrincipal,menuListagens,menuFuncionario,menuTecnico,menuOrcamento,menuReparacao,menuServico,menuExpresso;

    public static void main(String[] args) throws IOException {
        new SistemaAPP().run();
    }

    private SistemaAPP() {

        this.menuPrincipal = new Menu(new String[]{"Login","Aceder Listagens"});
        this.menuListagens = new Menu(new String[]{"Lista de Tecnicos", "Lista de Funcionarios", "Lista de Interveçoes"});
        this.menuFuncionario = new Menu(new String[]{"Registar Equipamento", "Registar Entrega"});
        this.menuTecnico = new Menu(new String[]{"Fazer Orçamento", "Confirmar/Recusar Orçamento","Fazer Reparação"});
        this.menuOrcamento = new Menu(new String[]{"Adicionar Passo", "Concluir Plano","Reparacao Inpossivel"});
        this.menuServico = new Menu(new String[]{"Geral", "Expresso"});
        this.menuReparacao = new Menu(new String[]{"Concluir Passo"});
        this.menuExpresso = new Menu(new String[]{"Concluir Reparacao"});

        sf.registarTecnico("t","123");
        sf.registarFuncionario("f","123");
        sf.registarFuncionario("funcionario","funcionario");
        sf.registarTecnico("tecnico","tecnico");

        sf.registarTipoDeExpresso("Ecra Partido",29.99);
        sf.registarTipoDeExpresso("Nova Bateria",49.99);
        sf.registarTipoDeExpresso("Limpar Equipamento",14.99);
        sf.registarTipoDeExpresso("Instalar Software",4.99);
        sf.registarTipoDeExpresso("Limpar Virus",19.99);
        sf.registarTipoDeExpresso("Trocar Teclado",39.99);

        /* carregar o estado*/
        try
        {
            this.sf = SistemasFacadeDAO.loadEstado("SGCR.dat");
            this.sf.arquivarOrcamentos();
        } catch (ClassNotFoundException | IOException cnfe)
        {
            cnfe.printStackTrace();
        }
    }

    public void run() throws IOException {
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        String username, password;

        do {
            menuPrincipal.executa();
            switch (menuPrincipal.getOpcao()) {
                case 1 -> { // Login
                    System.out.print("Username: ");
                    username = systemIn.readLine();
                    System.out.print("Password: ");
                    password = systemIn.readLine();
                    String worker;
                    if ((worker = sf.isTrabalhador(username, password)) != null) {
                        if (worker.equals(Tecnico.class.getSimpleName()))
                            menuTecnico(username, sf, systemIn);
                        else
                            menuFuncionario(username, sf, systemIn);
                    } else System.out.println("Worker not registered!");
                }
                case 2 -> // Aceder Listagens
                        menuListagens(sf, systemIn);
            }
        } while (menuPrincipal.getOpcao() != 0);

        /*guardar estado*/
        try {
            SistemasFacadeDAO.storeEstado(sf,"SGCR.dat");
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.out.println("Erro a gravar estado!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void menuListagens(ISistemaFacade isf, BufferedReader systemIn) throws IOException {
        boolean quit;

        do {
            menuListagens.executa();
            switch (menuListagens.getOpcao()) {
                case 1 -> { // Lista de Tecnicos
                    List<Trabalhador> todosTecnicos = isf.getListaTecnicos();
                    if(todosTecnicos.size()>0) {
                        for (Trabalhador tec : todosTecnicos) System.out.println(tec);
                        System.out.println("0 - Sair");
                        System.out.print("Opção: ");
                        quit = true;
                        while (quit) quit = !systemIn.readLine().equals("0");
                    }else System.out.println("Não existem tecnicos registados!");
                }
                case 2 -> { // Lista de Funcionarios
                    List<Trabalhador> todosFuncionarios = isf.getListaFuncionarios();
                    if (todosFuncionarios.size()>0) {
                        for (Trabalhador fun : todosFuncionarios) System.out.println(fun);
                        System.out.println("0 - Sair");
                        System.out.print("Opção: ");
                        quit = true;
                        while (quit) quit = !systemIn.readLine().equals("0");
                    }else System.out.println("Não existem funcionarios registados!");
                }
                case 3 -> { // Lista de Interveçoes
                    boolean flag = true;
                    List<Trabalhador> tecnicos = isf.getListaTecnicos();
                    if (tecnicos.size()>0) {
                        do {
                            int t;
                            int counter = 1;
                            for (Trabalhador tec : tecnicos) {
                                System.out.println(counter + ": " + tec);
                                counter++;
                            }
                            System.out.println("0 - Sair");
                            System.out.print("Opção: ");
                            t = Integer.parseInt(systemIn.readLine());
                            if (t <= tecnicos.size() && t > 0) {
                                List<TipoDeExpresso> expressos = isf.getListaIntervencoesExpressos(tecnicos.get(t - 1).getIdentificador());
                                List<Plano> planos = isf.getListaIntervencoesPlanos(tecnicos.get(t - 1).getIdentificador());
                                if (planos.size()>0 || expressos.size()>0) {
                                    counter = 1;
                                    if (planos.size()>0) {
                                        for (Plano p : planos) System.out.println("Registo " + counter + ":\n" + p);
                                    }
                                    if (expressos.size()>0){
                                        for (TipoDeExpresso ex : expressos) System.out.println("Registo " + counter + ":\n" + ex);
                                    }
                                    System.out.println("0 - Sair");
                                    System.out.print("Opção: ");
                                    quit = true;
                                    while (quit) quit = !systemIn.readLine().equals("0");
                                }else System.out.println("Não existem planos registados!");
                            } else if (t == 0) flag = false;
                            else {
                                System.out.println("Escolha tecnico valido!!");
                            }
                        } while (flag);
                    }else System.out.println("Não existem tecnicos registados!");
                }
            }
        } while (menuListagens.getOpcao() != 0);
    }

    private void menuFuncionario(String username, ISistemaFacade isf, BufferedReader systemIn) throws IOException {

        String nif,email,numero;
        boolean quit, flag;
        int equip, counter, servico;

        do {
            menuFuncionario.executa();
            switch (menuFuncionario.getOpcao()) {
                case 1 -> { // Registar Equipamento
                    String descricao;
                    Servico serv = null;
                    quit = false;

                    menuServico.executa();
                    switch (menuServico.getOpcao()) {
                        case 0 -> // sair
                                quit=true;
                        case 1 ->   // Geral
                                serv = new Geral();
                        case 2 -> { // Expresso
                            if (!isf.recusarExpresso()) {
                                List<TipoDeExpresso> todosServicos = isf.getServicosExpresso();
                                flag = true;
                                do {
                                    counter = 1;
                                    for (TipoDeExpresso e : todosServicos) {
                                        System.out.println(counter + ": " + e);
                                        counter++;
                                    }

                                    System.out.print("Escolha servico expresso: ");
                                    servico = Integer.parseInt(systemIn.readLine());

                                    if (servico <= todosServicos.size() && servico > 0) {
                                        serv = new Expresso(todosServicos.get(servico - 1));
                                        flag = false;
                                    } else System.out.println("Escolha um servico válido!");
                                } while (flag);
                            }else {
                                System.out.println("Servico Expresso Indisponivel!");
                                quit = true;
                            }
                        }
                    }

                    if (!quit) {
                        System.out.print("NIF: ");
                        nif = systemIn.readLine();
                        if(isf.clienteNovo(nif)){
                            System.out.print("Email: ");
                            email = systemIn.readLine();
                            System.out.print("Numero: ");
                            numero = systemIn.readLine();
                            isf.registarCliente(nif,email,numero);
                        }
                        System.out.print("Descricao: ");
                        descricao = systemIn.readLine();

                        isf.registarEquipamento(username, descricao, nif, serv);

                        System.out.println("Equipamento Registado!");
                    }
                }
                case 2 -> { // Registar Entrega
                    System.out.print("NIF: ");
                    nif = systemIn.readLine();
                    List<Equipamento> reparados = isf.equipReparadosCliente(nif);
                    if(reparados!=null && reparados.size()>0){
                        flag = true;
                        do {
                            counter = 1;
                            for (Equipamento e : reparados) {System.out.println(counter + ": " + e);counter++;}

                            System.out.print("Escolha Equipamento a entregar: ");
                            equip = Integer.parseInt(systemIn.readLine());

                            if (equip <= reparados.size() && equip > 0) {
                                isf.registarEntrega(username,reparados.get(equip-1).getId(),nif);
                                System.out.println("Equipamento entregue!");
                                flag=false;
                            } else System.out.println("Escolha um equipamento válido!");
                        } while (flag);
                    }else System.out.println("Não existem Equipamentos preparados para entregar!");

                }
            }
        } while (menuFuncionario.getOpcao() != 0);
    }

    private void menuTecnico(String username, ISistemaFacade isf, BufferedReader systemIn) throws IOException {

        boolean flag,done,out;
        String tempo,preco,descricao,estado,nif;
        int equip, orc, counter;
        do {
            menuTecnico.executa();
            switch (menuTecnico.getOpcao()) {
                case 1 -> { // Fazer Orçamento
                    List<Equipamento> porPlanear = isf.getListaPedidosOrcamento();
                    Equipamento eq=null;
                    if (porPlanear.size()>0) {
                        flag = true;
                        do {
                            counter = 1;
                            for (Equipamento e : porPlanear) {System.out.println(counter + ": " + e);counter++;}

                            System.out.print("Escolha Equipamento para orcamentar:");
                            equip = Integer.parseInt(systemIn.readLine());

                            if (equip <= porPlanear.size() && equip > 0) {
                                eq = porPlanear.get(equip-1);
                                flag=false;
                            } else System.out.println("Escolha um equipamento válido!");
                        } while (flag);
                        System.out.println("\nDescricao: " + eq.getDescricao());
                        Plano plano = eq.getPlano();if (plano==null) plano = new Plano();
                        done = false;
                        do {
                            System.out.print(plano);
                            menuOrcamento.executa();
                            switch (menuOrcamento.getOpcao()) {
                                case 0 -> {
                                    done = true;
                                    isf.introduzirPlanoIncompleto(username, eq.getId(), plano);
                                }
                                case 1 -> {
                                    System.out.print("Descrição da etapa: ");
                                    descricao = systemIn.readLine();
                                    System.out.print("Duracao(horas): ");
                                    tempo = systemIn.readLine();
                                    System.out.print("Preço: ");
                                    preco = systemIn.readLine();
                                    plano.addPasso(descricao, tempo, preco);
                                }
                                case 2 -> {
                                    if(plano.size()>0) {
                                        isf.registarOrcamento(username, eq.getId(), plano);
                                        done = true;
                                        System.out.println("Orcamento Concluido!");
                                        System.out.println("Alertar cliente: " + isf.getContactoEmail(eq.getNif()));
                                    }else System.out.println("Plano não pode ser vazio!");
                                }
                                case 3 -> {
                                    System.out.print("Motivo: ");
                                    String motivo = systemIn.readLine();
                                    isf.arquivarOrcamento(eq.getId(), motivo);
                                    done = true;
                                    System.out.println("Orcamento Arquivado!");
                                    System.out.println("Alertar cliente: " + isf.getContactoEmail(eq.getNif()));
                                }
                            }
                        }while (!done);

                    }else System.out.println("Não existem equipamentos por orcamentar!");
                }

                case 2 -> { // Confirmar Orcamento
                    System.out.print("NIF: ");
                    nif = systemIn.readLine();
                    List<Equipamento> orcamentos = isf.pedidosCliente(nif);
                    if(orcamentos!=null && orcamentos.size()>0){
                        flag = true;
                        do {
                            counter = 1;
                            for (Equipamento e : orcamentos) {System.out.println(counter + ": " + e);counter++;}

                            System.out.print("Escolha orcamento a confirmar: ");
                            orc = Integer.parseInt(systemIn.readLine());

                            if (orc <= orcamentos.size() && orc > 0) {
                                out = false;
                                while (!out) {
                                    System.out.print("Cliente aceita orcamento? [s/n]: ");
                                    estado = systemIn.readLine();
                                    if (estado.equals("n")) {
                                        System.out.print("Motivo: ");
                                        String motivo = systemIn.readLine();
                                        isf.arquivarOrcamento(orcamentos.get(orc-1).getId(),motivo);
                                        System.out.println("Pedido de orçamento arquivado!");
                                        out=true;
                                    } else if (estado.equals("s")) {
                                        isf.aceitarOrcamento(orcamentos.get(orc-1).getId());
                                        System.out.println("Orcamento Confirmado!");
                                        out=true;
                                    }
                                }
                                flag=false;
                            } else System.out.println("Escolha um equipamento válido!");
                        } while (flag);
                    }else System.out.println("Não existem orcamentos por confirmar!");
                }

                case 3 -> {// Fazer Reparação

                    List<Equipamento> porReparar = isf.getListaEquipReparar();
                    Equipamento eq=null;
                    if (porReparar.size()>0) {
                        flag = true;
                        do {
                            counter = 1;
                            for (Equipamento e : porReparar) {System.out.println(counter + ": " + e);counter++;}

                            System.out.print("Escolha Equipamento para reparar: ");
                            equip = Integer.parseInt(systemIn.readLine());

                            if (equip <= porReparar.size() && equip > 0) {
                                eq = porReparar.get(equip-1);
                                flag=false;
                            } else System.out.println("Escolha um equipamento válido!");
                        } while (flag);
                        System.out.println("\nDescricao: " + eq.getDescricao());
                        done = false;
                        if (eq.getServico() instanceof Geral) {
                            do {
                                System.out.print(eq.getPlano());
                                menuReparacao.executa();
                                switch (menuReparacao.getOpcao()) {
                                    case 0 -> {
                                        done = true;
                                    }
                                    case 1 -> {
                                        System.out.print("Duracao(horas): ");
                                        tempo = systemIn.readLine();
                                        isf.registarPassoFeito(eq.getId(),Integer.parseInt(tempo));
                                        if (eq.getPlano().isLastPasso()) {
                                            isf.concluirReparacao(username, eq.getId());
                                            done = true;
                                            System.out.println("Reparação concluida!");
                                            System.out.println("Notificar cliente: " + isf.getContactoEmail(eq.getNif()));
                                        } else {
                                            System.out.println("Passo concluido!");
                                        }
                                    }
                                }
                            } while (!done);
                        }else {
                            System.out.println("Servico Expresso: " + ((Expresso)eq.getServico()).tipoDeExpresso());
                            menuExpresso.executa();
                            switch (menuExpresso.getOpcao()) {
                                case 0 -> {
                                }
                                case 1 -> {
                                    isf.concluirReparacao(username, eq.getId());
                                    System.out.println("Reparação concluida!");
                                    System.out.println("Notificar cliente: " + isf.getContactoNumero(eq.getNif()));
                                }
                            }
                        }
                    }else System.out.println("Não existem equipamentos por reparar!");
                }
            }
        } while (menuTecnico.getOpcao() != 0);
    }
}
