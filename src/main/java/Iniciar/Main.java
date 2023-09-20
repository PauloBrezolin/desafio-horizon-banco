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
                System.out.println("VOCE TEM UMA CONTA");
                //int conta = EscolherConta();
                // Operacoes(conta);
            } else {
                CadastroConta();
            }
        } else {
            CadastroUsuario();
        }
        // if LoginUsuario return true = LoginConta() (if return false CadastroConta())
        // if return true = Operacoes(); (Depositar, Sacar(), RealizarTrasferencia() ou ConsultarSaldo())
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

        EntityFactory.CadastroUsuario(nome, telefone, cpf, em);
    }

    public static boolean LoginUsuario() {
        EntityManager em = EntityFactory.getEntityFactory();

        boolean validacaoCPF = false;

        System.out.println("Seja bem vindo ao Banco do Paulo!");
        validacaoCPF = EntityFactory.ValidarCPF(cpf, em);
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
        int tipoConta;
        System.out.println("Você ainda não tem uma conta!");
        System.out.println("Crie uma conta abaixo.");

        pessoa = EntityFactory.getPessoa(cpf, em);
        pessoa_id = EntityFactory.getID(cpf, em);

        do {
            System.out.println("Escolha o tipo da sua conta (1 PRA CORRENTE E 2 PRA POUPANÇA):");
            tipoConta = scanner.nextInt();
            if (tipoConta != 1 && tipoConta != 2) {
                System.out.println("Tipo de conta inválido! Você deve digitar 1 ou 2.");
            }
        } while ((tipoConta != 1 && tipoConta != 2) || (!EntityFactory.validaTipoConta(pessoa_id, tipoConta, em)));
        
        do {
            numero = gerador.nextInt(10000000);
        } while (!EntityFactory.validaNumeroConta(numero, em));
        EntityFactory.CadastroConta(pessoa, numero, digito, saldo, tipoConta, em);
    }
    
    public static boolean LoginConta() {
        EntityManager em = EntityFactory.getEntityFactory();

        long pessoa_id = EntityFactory.getID(cpf, em);
        boolean validacaoConta = EntityFactory.ValidarConta(pessoa_id, em);
        return validacaoConta;
    }
    
//     public static int EscolherConta() {
//         int numero;

//         return numero;
//     }
}
