package br.com.compass.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    private int usuarioId;
    private String numeroConta;
    private BigDecimal saldo;
    private String tipoConta;
    private LocalDate dataCriacao;

    public Account(int usuarioId, String numeroConta, BigDecimal saldo, String tipoConta, LocalDate dataCriacao) {
        this.usuarioId = usuarioId;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.dataCriacao = dataCriacao;
    }

    public int getUsuarioId() {
        return usuarioId;
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
