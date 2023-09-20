package EntityFactory;

import Dominio.Conta;
import Dominio.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class EntityFactory {
    
    long id;

    public static EntityManager getEntityFactory(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");

        return emf.createEntityManager();
    }

    public static boolean validarCPF(String cpf, EntityManager em){

        TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class);
        query.setParameter("cpf", cpf); //seleciona pelo cpf

        List<Pessoa> pessoas = query.getResultList(); //coloca resultado da query dentro da lista pessoas

        if (!pessoas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static long getID(String cpf, EntityManager em){

        TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class);
        query.setParameter("cpf", cpf); //seleciona pelo cpf

        List<Pessoa> pessoas = query.getResultList(); //coloca resultado da query dentro da lista pessoas

        Pessoa pessoaEncontrada = pessoas.get(0);
        long id = pessoaEncontrada.getId();
        return id;
    }

    public static Pessoa getPessoa(String cpf, EntityManager em){

        TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class);
        query.setParameter("cpf", cpf); //seleciona pelo cpf

        List<Pessoa> pessoas = query.getResultList(); //coloca resultado da query dentro da lista pessoas

        Pessoa pessoaEncontrada = pessoas.get(0);
        return pessoaEncontrada;
    }

    public static void cadastroUsuario(String nome, String telefone, String cpf, EntityManager em){

        Pessoa pessoa = new Pessoa(nome, telefone, cpf); //cria uma nova pessoa

		em.getTransaction().begin(); //começar transação com o banco de dados
		em.persist(pessoa);
		em.getTransaction().commit(); //confirmar transacao com o banco de dados
		System.out.println("Usuário criado!");
    }

    public static boolean validarConta(Long pessoa_id, EntityManager em) {

        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
    
        List<Conta> contas = query.getResultList();
    
        if (!contas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static void cadastroConta(Pessoa pessoa_id, int numero, int digito, double saldo, int tipo_conta, EntityManager em){

        Conta conta = new Conta(pessoa_id, numero, digito, saldo, tipo_conta); //cria uma nova pessoa

		em.getTransaction().begin(); //começar transação com o banco de dados
		em.persist(conta);
		em.getTransaction().commit(); //confirmar transacao com o banco de dados
		System.out.println("Conta criada!");
    }

    public static boolean validaNumeroConta(int numero, EntityManager em) {
        
        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.numero = :numero", Conta.class);
        query.setParameter("numero", numero);
    
        List<Conta> contas = query.getResultList();
    
        if (contas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatipo_conta(Long pessoa_id, int tipo_conta, EntityManager em) {

        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id AND c.tipo_conta = :tipo_conta", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
        query.setParameter("tipo_conta", tipo_conta);
    
        List<Conta> contas = query.getResultList();
    
        if (contas.isEmpty()) {
            return true;
        } else {
            System.out.println("Você não pode ter duas contas do mesmo tipo!");
            return false;
        }
    }

    public static int contarContas(Long pessoa_id, EntityManager em) {
        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
    
        List<Conta> contas = query.getResultList();
        return contas.size();
    }

    public static int getNumeroConta(Long pessoa_id, EntityManager em) {
        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
    
        List<Conta> contas = query.getResultList();

        Conta contaEncontrada = contas.get(0);
        int numeroConta = contaEncontrada.getNumero();
        return numeroConta;
    }

    public static int getNumeroContaByTipo(Long pessoa_id, int tipo_conta, EntityManager em) {

        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id AND c.tipo_conta = :tipo_conta", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
        query.setParameter("tipo_conta", tipo_conta);
    
        List<Conta> contas = query.getResultList();

        Conta contaEncontrada = contas.get(0);
        int numeroConta = contaEncontrada.getNumero();
        return numeroConta;
    }

    public static int getTipoConta(Long pessoa_id, EntityManager em) {

        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
    
        List<Conta> contas = query.getResultList();

        Conta contaEncontrada = contas.get(0);
        int tipo_conta = contaEncontrada.getTipo_conta();
        return tipo_conta;
    }

}
