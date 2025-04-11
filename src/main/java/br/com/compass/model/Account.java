package br.com.compass.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    private long userId;
    private String numeroConta;
    private BigDecimal saldo;
    private String tipoConta;
    private LocalDate dataCriacao;

    public Account(long userId, String numeroConta, BigDecimal saldo, String tipoConta, LocalDate dataCriacao) {
        this.userId = userId;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.dataCriacao = dataCriacao;
    }

    public long getUserId() {
        return userId;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
