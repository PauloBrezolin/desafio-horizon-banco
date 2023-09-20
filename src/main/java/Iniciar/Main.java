package Iniciar;

import EntityFactory.EntityFactory;
import javax.persistence.EntityManager;

import Dominio.Pessoa;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static String cpf;

    public static void setCpf(String cpf) {
        Main.cpf = cpf;
    }

    public static void main(String[] args) {

        String cpf = solicitarCPF();
        setCpf(cpf);

        if (LoginUsuario()) {
            if (LoginConta()) {
                int conta = EscolherConta();
                // Operacoes(conta);
            } else {
                CadastroConta();
            }
        } else {
            CadastroUsuario();
        }
        // if LoginUsuario return true = LoginConta() (if return false CadastroConta())
        // if return true = Operacoes(); (Depositar, Sacar(), RealizarTrasferencia() ou
        // ConsultarSaldo())
        // if LoginUsuario return false = criarPessoa().
    }

    private static String solicitarCPF() {

        Scanner scanner = new Scanner(System.in);
        String cpf;

        do {
            System.out.println("Digite o seu CPF (SÓ NÚMEROS):");
            cpf = scanner.nextLine();
            if (!cpf.matches("\\d{11}")) {
                System.out.println("CPF inválido! O CPF deve conter exatamente 11 dígitos numéricos.");
            }
        } while (!cpf.matches("\\d{11}"));

        return cpf;
    }

    public static void CadastroUsuario() {

        Scanner scanner = new Scanner(System.in);
        EntityManager em = EntityFactory.getEntityFactory();

        String nome;
        String telefone;
        System.out.println("Você ainda não está cadastrado!");
        System.out.println("Realize seu cadastro abaixo.");

        do {
            System.out.println("Digite o seu nome:");
            nome = scanner.nextLine();
            if (!nome.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Nome inválido! O nome deve conter apenas letras.");
            }
        } while (!nome.matches("^[a-zA-Z\\s]+$"));

        do {
            System.out.println("Digite o seu telefone: (COM DDD E SÓ NÚMEROS)");
            telefone = scanner.nextLine();
            if (!telefone.matches("\\d{11}")) {
                System.out.println("Telefone inválido! O telefone deve conter exatamente 11 dígitos numéricos.");
            }
        } while (!telefone.matches("\\d{11}"));

        EntityFactory.cadastroUsuario(nome, telefone, cpf, em);
    }

    public static boolean LoginUsuario() {

        EntityManager em = EntityFactory.getEntityFactory();

        boolean validacaoCPF = false;

        System.out.println("Seja bem vindo ao Banco do Paulo!");
        validacaoCPF = EntityFactory.validarCPF(cpf, em);
        return validacaoCPF;
    }

    public static void CadastroConta() {

        Scanner scanner = new Scanner(System.in);
        Random gerador = new Random();
        EntityManager em = EntityFactory.getEntityFactory();

        int digito = gerador.nextInt(10);
        int numero;
        long pessoa_id;
        Pessoa pessoa;
        double saldo = 0;
        int tipo_conta;
        System.out.println("Vamos criar sua conta!");

        pessoa = EntityFactory.getPessoa(cpf, em);
        pessoa_id = EntityFactory.getID(cpf, em);

        do {
            System.out.println("Escolha o tipo da sua conta (1 PRA CORRENTE E 2 PRA POUPANÇA):");
            tipo_conta = scanner.nextInt();
            if (tipo_conta != 1 && tipo_conta != 2) {
                System.out.println("Tipo de conta inválido! Você deve digitar 1 ou 2.");
            }
        } while ((tipo_conta != 1 && tipo_conta != 2) || (!EntityFactory.validatipo_conta(pessoa_id, tipo_conta, em)));

        do {
            numero = gerador.nextInt(10000000);
        } while (!EntityFactory.validaNumeroConta(numero, em));
        EntityFactory.cadastroConta(pessoa, numero, digito, saldo, tipo_conta, em);
    }

    public static boolean LoginConta() {

        EntityManager em = EntityFactory.getEntityFactory();

        long pessoa_id = EntityFactory.getID(cpf, em);
        boolean validacaoConta = EntityFactory.validarConta(pessoa_id, em);
        return validacaoConta;
    }

    public static int EscolherConta() {

        int numero = 0;
        int escolha;

        Scanner scanner = new Scanner(System.in);
        EntityManager em = EntityFactory.getEntityFactory();
        long pessoa_id = EntityFactory.getID(cpf, em);
        int numeroDeContas = EntityFactory.contarContas(pessoa_id, em);
        int tipo_conta = EntityFactory.getTipoConta(pessoa_id, em);

        if (numeroDeContas == 1) {
            if (tipo_conta == 1) {
                do {
                    System.out.println("Você já tem uma conta corrente, deseja criar uma conta poupança? (1 = SIM, 2 = NÃO)");
                    escolha = scanner.nextInt();
                    if (escolha != 1 && escolha != 2) {
                        System.out.println("Opção inválida! Você deve digitar 1 ou 2.");
                    }
                } while (escolha != 1 && escolha != 2);

                if (escolha == 1) {
                    CadastroConta();
                    System.exit(1);
                }
            } else if (tipo_conta == 2) {
                do {
                    System.out.println("Você já tem uma conta poupança, deseja criar uma conta corrente? (1 = SIM, 2 = NÃO)");
                    escolha = scanner.nextInt();
                    if (escolha != 1 && escolha != 2) {
                        System.out.println("Opção inválida! Você deve digitar 1 ou 2.");
                    }
                } while (escolha != 1 && escolha != 2);

                if (escolha == 1) {
                    CadastroConta();
                    System.exit(1);
                }
            }

            numero = EntityFactory.getNumeroConta(pessoa_id, em);
        } else if (numeroDeContas == 2) {
            do {
                System.out.println("Você tem duas contas, qual deseja usar? (1 PRA CORRENTE E 2 PRA POUPANÇA)");
                tipo_conta = scanner.nextInt();
                numero = EntityFactory.getNumeroContaByTipo(pessoa_id, tipo_conta, em);
                if (tipo_conta != 1 && tipo_conta != 2) {
                    System.out.println("Tipo de conta inválido! Você deve digitar 1 ou 2.");
                }
            } while (tipo_conta != 1 && tipo_conta != 2);
        }

        return numero;
    }
}
