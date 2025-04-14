package br.com.compass.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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

		Transaction transaction = new Transaction(0, user.getId(), accountRepository.getAccountIdByUserId(user.getId()),
																														
																														
				amount, new Date(), "DEPOSITO", "Depósito realizado na conta", null);

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

		Transaction transaction = new Transaction(0, user.getId(), accountRepository.getAccountIdByUserId(user.getId()),
				-amount, new Date(), "SAQUE", "Saque realizado da conta", null);

		transactionRepository.save(transaction);

		System.out.println("Saque realizado com sucesso!");
	}

	public void transfer(User user) {
		System.out.print("Informe o CPF do destinatário: ");
		String destinoCpf = scanner.nextLine().replaceAll("[^0-9]", "");

		int destinoId = accountRepository.getAccountIdByCpf(destinoCpf);

		if (destinoId == 0 || destinoId == user.getId()) {
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
		accountRepository.updateBalance(destinoId, amount);

		Transaction transaction = new Transaction(
			    user.getId(),
			    accountRepository.getAccountIdByUserId(user.getId()), 
			    -amount,
			    new Date(),
			    "TRANSFERENCIA",
			    "Transferência realizada",
			    (long) destinoId
			);

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
	
	public void listarExtrato(User user) {
	    List<Transaction> transacoes = transactionRepository.findAllByUserId(user.getId());

	    if (transacoes.isEmpty()) {
	        System.out.println("Nenhuma transação encontrada.");
	        return;
	    }

	    System.out.println("========= Extrato Bancário =========");
	    for (Transaction t : transacoes) {
	        System.out.printf("ID: %d | Tipo: %s | Valor: %.2f | Data: %s",
	                t.getId(), t.getType(), t.getAmount(), t.getDate());
	        if (t.getDestinationUserId() != null) {
	            System.out.print(" | Destinatário: " + t.getDestinationUserId());
	        }
	        System.out.println();
	    }
	}
	public void exportarExtratoCSV(User user) {
	    List<Transaction> transacoes = transactionRepository.findAllByUserId(user.getId());

	    if (transacoes.isEmpty()) {
	        System.out.println("Nenhuma transação para exportar.");
	        return;
	    }

	   
	    String nomeArquivo = "extrato_user_" + user.getId() + ".csv";
	    String caminhoArquivo = "C:\\temp2\\" + nomeArquivo;

	   
	    File pasta = new File("C:\\temp2");
	    if (!pasta.exists()) {
	        pasta.mkdirs(); 
	    }

	    try (FileWriter writer = new FileWriter(caminhoArquivo)) {
	       
	        writer.append("ID,Tipo,Valor,Data,Destino\n");

	        
	        for (Transaction t : transacoes) {
	            writer.append(String.format(
	                "%d,%s,%.2f,%s,%s\n",
	                t.getId(),
	                t.getType(),
	                t.getAmount(),
	                t.getDate(),
	                t.getDestinationUserId() != null ? t.getDestinationUserId() : ""
	            ));
	        }

	        System.out.println("Extrato exportado com sucesso para: " + caminhoArquivo);

	    } catch (IOException e) {
	        System.out.println("Erro ao exportar extrato: " + e.getMessage());
	    }
	}


}
