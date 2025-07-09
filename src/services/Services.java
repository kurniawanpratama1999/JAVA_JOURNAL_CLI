package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.Control;
import models.Transaction;
import utils.LocalDateAdapter;
import utils.Response;

import java.time.LocalDate;
import java.util.Stack;

public class Services {

    private final Control control = new Control();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

    public String addNewAccount(String name) {return getResponse(control.addNewAccount(name));}
    public String deleteExistingAccountName(String name) {return getResponse(control.deleteExistingAccountName(name));}
    public String updateExistingAccountName(String name, String newName) {return getResponse(control.updateExistingAccountName(name, newName));}
    public String listAccount() {return getResponse(control.listAccount());}

    public String addNewTransaction(Stack<Transaction> transactions) {return getResponse(control.addNewTransaction(transactions));}
    public String deleteExistingTransaction(Long ref) {return getResponse(control.deleteExistingTransaction(ref));}
    public String listTransaction() {return getResponse(control.listTransaction());}
    public String getDataTransaction(Long ref) {return getResponse(control.getDataTransaction(ref));}
    public String updateExistingTransactionDate(Long ref, Long id, LocalDate newDate) {return getResponse(control.updateExistingTransactionDate(ref, id, newDate));}
    public String updateExistingTransactionAccount(Long ref, Long id, String newAccount) {return getResponse(control.updateExistingTransactionAccount(ref, id, newAccount));}
    public String updateExistingTransactionDebit(Long ref, Long id, Long newDebit) {return getResponse(control.updateExistingTransactionDebit(ref, id, newDebit));}
    public String updateExistingTransactionCredit(Long ref, Long id, Long newCredit) {return getResponse(control.updateExistingTransactionCredit(ref, id, newCredit));}
    public String updateExistingTransactionDetail(Long ref, Long id, String newDetail) {return getResponse(control.updateExistingTransactionDetail(ref, id, newDetail));}

    public void save () {control.save();}

    private <T> String getResponse (Response<T> response) {
        String msg = gson.toJson(response.msg());
        return response.success()
                ?  "\u001B[32m"+ "~".repeat(msg.length()) + "\n" + msg + "\n" + "~".repeat(msg.length()) + "\u001B[0m"
                : "\u001B[31m" + "~".repeat(msg.length()) + "\n" + msg + "\n" + "~".repeat(msg.length()) + "\u001B[0m";
    }
}
