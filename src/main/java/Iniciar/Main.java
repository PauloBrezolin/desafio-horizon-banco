package Iniciar;

import EntityFactory.EntityFactory;
import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (LoginUsuario()) {
            if (LoginConta()) {
                System.out.println("OPERACOES");
                // Operacoes();
            } else {
                System.out.println("CADASTRO CONTA");
                // CadastroConta();
            }
        } else {
            CriarUsuario();
        }
        // if LoginUsuario return true = LoginConta() (if return false CadastroConta())
        // if return true = Operacoes(); (Depositar, Sacar(), RealizarTrasferencia() ou ConsultarSaldo())
        // if LoginUsuario return false = criarPessoa().
    }

    public static void CriarUsuario() {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = EntityFactory.getEntityFactory();

        String nome;
        String telefone;
        String cpf;
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

        do {
            System.out.println("Digite o seu CPF (SÓ NÚMEROS):");
            cpf = scanner.nextLine();
            if (!cpf.matches("\\d{11}")) {
                System.out.println("CPF inválido! O CPF deve conter exatamente 11 dígitos numéricos.");
            }
        } while (!cpf.matches("\\d{11}"));

        scanner.close();
        EntityFactory.CadastroUsuario(nome, telefone, cpf, em);
    }

    public static boolean LoginUsuario() {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = EntityFactory.getEntityFactory();

        String cpf;
        boolean validacaoCPF = false;

        System.out.println("Seja bem vindo ao Banco do Paulo!");
        System.out.println("SE NÃO TIVER CADASTRO, DIGITE 1");

        do {
            System.out.println("Digite o seu CPF (SÓ NÚMEROS):");
            cpf = scanner.nextLine();
            if (cpf.matches("\\d{11}")) {
                validacaoCPF = EntityFactory.ValidarCPF(cpf, em);
                return validacaoCPF;
            } else if (cpf.equals("1")) {
                return false;
            } else {
                System.out.println("CPF inválido. Certifique-se de que tem exatamente 11 dígitos numéricos.");
            }
        } while ((!cpf.matches("\\d{11}")) || (cpf.equals("1")));
        scanner.close();
        return false;
    }

    public static boolean LoginConta() {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = EntityFactory.getEntityFactory();

        String cpf;
        boolean validacaoConta = false;

        System.out.println("SE NÃO TIVER UMA CONTA, DIGITE 1");

        do {
            System.out.println("Digite o seu CPF novamente (SÓ NÚMEROS):");
            cpf = scanner.nextLine();
            if (cpf.matches("\\d{11}")) {
                long pessoa_id = EntityFactory.getID(cpf, em);
                validacaoConta = EntityFactory.ValidarConta(pessoa_id, em);
                return validacaoConta;
            } else if (cpf.equals("1")) {
                return false;
            } else {
                System.out.println("CPF inválido. Certifique-se de que tem exatamente 11 dígitos numéricos.");
            }
        } while ((!cpf.matches("\\d{11}")) || (cpf.equals("1")));
        scanner.close();
        return false;
    }
}
