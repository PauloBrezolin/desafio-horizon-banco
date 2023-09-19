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

    public static boolean ValidarCPF(String cpf, EntityManager em){

        TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class);
        query.setParameter("cpf", cpf); //seleciona pelo cpf

        List<Pessoa> pessoas = query.getResultList(); //coloca resultado da query dentro da lista pessoas

        if (!pessoas.isEmpty()) {
            Pessoa pessoaEncontrada = pessoas.get(0);
            System.out.println(pessoaEncontrada);
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
        System.out.println(id);
        return id;
    }

    public static void CadastroUsuario(String nome, String telefone, String cpf, EntityManager em){
        Pessoa pessoa = new Pessoa(nome, telefone, cpf); //cria uma nova pessoa

		em.getTransaction().begin(); //começar transação com o banco de dados
		em.persist(pessoa);
		em.getTransaction().commit(); //confirmar transacao com o banco de dados
		System.out.println("Usuário criado!");
    }

    public static boolean ValidarConta(Long pessoa_id, EntityManager em) {
        TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.pessoa.id = :pessoa_id", Conta.class);
        query.setParameter("pessoa_id", pessoa_id);
    
        List<Conta> contas = query.getResultList();
    
        if (!contas.isEmpty()) {
            Conta contaEncontrada = contas.get(0);
            System.out.println(contaEncontrada);
            return true;
        } else {
            return false;
        }
    }
}
