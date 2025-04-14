package br.com.compass.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import br.com.compass.model.Reversal;
import br.com.compass.model.User;
import br.com.compass.repository.ReversalRepository;

public class ReversalService {
    private final Scanner scanner;
    private final ReversalRepository reversalRepository;

    public ReversalService(Scanner scanner) {
        this.scanner = scanner;
        this.reversalRepository = new ReversalRepository();
    }

    public void listarEstornosPendentes() {
        List<Reversal> pendentes = reversalRepository.getReversalsByStatus("PENDENTE");
        if (pendentes.isEmpty()) {
            System.out.println("Nenhum estorno pendente.");
        } else {
            System.out.println("Estornos pendentes:");
            for (Reversal reversal : pendentes) {
                System.out.println("ID: " + reversal.getId() +
                                   " | ID Transação: " + reversal.getTransactionId() +
                                   " | Data: " + reversal.getRequestDate());
            }
        }
    }

    public void solicitarEstorno(User user, int transactionId) {
        String motivo = "Solicitação de estorno pelo cliente";
        LocalDateTime dataSolicitacao = LocalDateTime.now();
        reversalRepository.solicitarEstorno((int) user.getId(), transactionId, motivo, dataSolicitacao);
        System.out.println("Solicitação de estorno enviada para análise.");
    }

    public void aprovarEstorno(int reversalId, long gerenteId) {
        boolean success = reversalRepository.aprovarEstorno(reversalId, gerenteId);
        if (success) {
            System.out.println("Estorno aprovado com sucesso.");
        } else {
            System.out.println("Não foi possível aprovar o estorno.");
        }
    }
}
