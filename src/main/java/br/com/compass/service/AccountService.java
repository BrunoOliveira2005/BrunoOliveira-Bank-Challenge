package br.com.compass.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import br.com.compass.model.Account;
import br.com.compass.repository.AccountRepository;

public class AccountService {
    private final AccountRepository accountRepository = new AccountRepository();

    public void criarConta(Account conta) {
        accountRepository.save(conta);
    }



    public void criarConta(int usuarioId, String tipoConta) {
        String numeroConta = gerarNumeroConta();
        BigDecimal saldoInicial = BigDecimal.ZERO;
        LocalDate dataCriacao = LocalDate.now();

        Account conta = new Account(usuarioId, numeroConta, saldoInicial, tipoConta, dataCriacao);
        accountRepository.save(conta);

        System.out.println("\n Conta criada com sucesso!");
        System.out.println("Número da conta: " + numeroConta);
    }

    private String gerarNumeroConta() {
        Random random = new Random();
        int numero = 100000 + random.nextInt(900000);
        return String.valueOf(numero);
    }
    
}
