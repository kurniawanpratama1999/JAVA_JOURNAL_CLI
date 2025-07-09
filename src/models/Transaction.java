package models;

import java.time.LocalDate;

public class Transaction {

    private Long id;
    private Long ref;
    private LocalDate date;
    private String account;
    private Long debit;
    private Long credit;
    private String detail;

    public Transaction(LocalDate date, String account, Long debit, Long credit, String detail) {
        this.date = date;
        this.account = account;
        this.debit = debit;
        this.credit = credit;
        this.detail = detail;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getRef() {return ref;}
    public void setRef(Long ref) {this.ref = ref;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public String getAccount() {return account;}
    public void setAccount(String account) {this.account = account;}

    public Long getDebit() {return debit;}
    public void setDebit(Long debit) {this.debit = debit;}

    public Long getCredit() {return credit;}
    public void setCredit(Long credit) {this.credit = credit;}

    public String getDetail() {return detail;}
    public void setDetail(String detail) {this.detail = detail;}
}