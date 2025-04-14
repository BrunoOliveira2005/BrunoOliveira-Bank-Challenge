package br.com.compass.model;

import java.time.LocalDateTime;

public class Reversal {
    private int id;
    private int transactionId;
    private String status;
    private LocalDateTime requestDate;
    private boolean approved;
    private LocalDateTime approvedAt;
    private Long approvedBy;
    private String motivo;

 
    public Reversal(int id, int transactionId, String status, LocalDateTime requestDate,
                    boolean approved, LocalDateTime approvedAt, Long approvedBy, String motivo) {
        this.id = id;
        this.transactionId = transactionId;
        this.status = status;
        this.requestDate = requestDate;
        this.approved = approved;
        this.approvedAt = approvedAt;
        this.approvedBy = approvedBy;
        this.motivo = motivo;
    }
    public Reversal(int id, int transactionId, String status, LocalDateTime requestDate,
                    boolean approved, LocalDateTime approvedAt, Long approvedBy) {
        this.id = id;
        this.transactionId = transactionId;
        this.status = status;
        this.requestDate = requestDate;
        this.approved = approved;
        this.approvedAt = approvedAt;
        this.approvedBy = approvedBy;
    }

   
    public Reversal(int id, int transactionId, String status, LocalDateTime requestDate, String motivo) {
        this.id = id;
        this.transactionId = transactionId;
        this.status = status;
        this.requestDate = requestDate;
        this.motivo = motivo;
    }

    public Reversal(int id, int transactionId, String status, LocalDateTime requestDate) {
        this.id = id;
        this.transactionId = transactionId;
        this.status = status;
        this.requestDate = requestDate;
    }

  
    public int getId() {
        return id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
