package Dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;

    private int digito = 7;

    private double saldo = 0;

    private int tipoConta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getNumero() {
        return numero;
    }

    public int getDigito() {
        return digito;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", pessoa=" + pessoa +
                ", numero=" + numero +
                ", digito=" + digito +
                ", saldo=" + saldo +
                ", tipoConta=" + tipoConta +
                '}';
    }
}