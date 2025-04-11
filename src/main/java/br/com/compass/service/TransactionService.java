package br.com.compass.service;

import java.util.Date;
import java.util.Scanner;

import br.com.compass.model.Transaction;
import br.com.compass.model.User;
import br.com.compass.repository.AccountRepository;
import br.com.compass.repository.TransactionRepository;

public class TransactionService {

    private final Scanner scanner;
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final AccountRepository accountRepository = new AccountRepository();

    public TransactionService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void deposit(User user) {
        System.out.print("Digite o valor para depósito: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Valor inválido.");
            return;
        }

        accountRepository.updateBalance(user.getId(), amount);

        Transaction transaction = new Transaction(
            0,
            user.getId(),
            accountRepository.getAccountIdByUserId(user.getId()), // precisa garantir que esse método existe
            amount,
            new Date(),
            "DEPOSITO",
            "Depósito realizado na conta",
            null
        );

        transactionRepository.save(transaction);

        System.out.println("Depósito realizado com sucesso!");
    }

    public void withdraw(User user) {
        System.out.print("Digite o valor para saque: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        double balance = accountRepository.getBalance(user.getId());

        if (amount <= 0 || amount > balance) {
            System.out.println("Valor inválido ou saldo insuficiente.");
            return;
        }

        accountRepository.updateBalance(user.getId(), -amount);

        Transaction transaction = new Transaction(
            0,
            user.getId(),
            accountRepository.getAccountIdByUserId(user.getId()),
            -amount,
            new Date(),
            "SAQUE",
            "Saque realizado da conta",
            null
        );

        transactionRepository.save(transaction);

        System.out.println("Saque realizado com sucesso!");
    }

    public void transfer(User user) {
        System.out.print("Informe o CPF do destinatário: ");
        String destinoCpf = scanner.nextLine();

        int destinoAccountId = accountRepository.getAccountIdByCpf(destinoCpf);
        int origemAccountId = accountRepository.getAccountIdByUserId(user.getId());

        if (destinoAccountId == 0 || destinoAccountId == origemAccountId) {
            System.out.println("Conta destinatária inválida.");
            return;
        }

        System.out.print("Digite o valor da transferência: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        double balance = accountRepository.getBalance(user.getId());

        if (amount <= 0 || amount > balance) {
            System.out.println("Valor inválido ou saldo insuficiente.");
            return;
        }

        accountRepository.updateBalance(user.getId(), -amount);
        accountRepository.updateBalanceByAccountId(destinoAccountId, amount); // novo método, se necessário

        Transaction transaction = new Transaction(
            0,
            user.getId(),
            origemAccountId,
            -amount,
            new Date(),
            "TRANSFERENCIA",
            "Transferência para conta de CPF " + destinoCpf,
            (long) destinoAccountId
        );

        transactionRepository.save(transaction);

        System.out.println("Transferência realizada com sucesso!");
    }

    public void checkBalance(User user) {
        double balance = accountRepository.getBalance(user.getId());
        System.out.println("Saldo atual: R$ " + String.format("%.2f", balance));
    }

    public void reverterTransacao(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId);

        if (transaction == null) {
            System.out.println("Transação não encontrada para estorno.");
            return;
        }

        long userId = transaction.getUserId();
        int accountId = transaction.getAccountId();
        double valor = transaction.getAmount();
        String tipo = transaction.getType();

        switch (tipo.toUpperCase()) {
            case "DEPOSITO":
                accountRepository.updateBalance(userId, -valor);
                break;
            case "SAQUE":
                accountRepository.updateBalance(userId, valor);
                break;
            case "TRANSFERENCIA":
                Long destinoId = transaction.getDestinationUserId();
                if (destinoId == null) {
                    System.out.println("Transferência sem destino registrado. Estorno cancelado.");
                    return;
                }
                accountRepository.updateBalance(destinoId, -valor);
                accountRepository.updateBalance(userId, valor);
                break;
            default:
                System.out.println("Tipo de transação não suportado para estorno.");
                return;
        }

        System.out.println("Transação estornada com sucesso.");
    }
}
