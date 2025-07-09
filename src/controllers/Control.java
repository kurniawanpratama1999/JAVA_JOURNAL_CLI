package controllers;

import controllers.interfaceOrAbstract.*;

import models.*;
import utils.*;

import java.time.LocalDate;
import java.util.*;


public class Control implements TransactionControl, AccountControl {
    private final Memories memories = new Memories();
    private Long id = memories.getID();
    private Long ref = memories.getRef();
    private final Set<Account> accounts = memories.getAccounts();
    private final Map<Long, LinkedHashSet<Transaction>> transactions = memories.getTransactions();

    @Override
    public Response<Account> addNewAccount(String name) {
        if (isAnyNameMatchWithGetName(name)) return Response.bad("account: " + name + " is already exist");

        Account a = new Account(name);
        return Response.good("new account is added", a);
    }

    @Override
    public Response<Account> deleteExistingAccountName(String name) {
        Iterator<Account> iterator = accounts.iterator();

        while (iterator.hasNext()) {
            Account a = iterator.next();
            if (name.equalsIgnoreCase(a.getName())) {
                iterator.remove();
                return Response.good("Account: " + name + " is removed", a);
            }
        }

        return Response.bad("Cannot find " + name + " account");
    }

    @Override
    public Response<Account> updateExistingAccountName(String name, String newName) {
        for (Account a : accounts) {
            if (name.equalsIgnoreCase(a.getName())) {
                a.setName(newName);
                return Response.good("Account: " + name + " is change to " + newName, a);
            }
        }

        return Response.bad("Cannot find " + name + " account");
    }

    @Override
    public Response<Collection<Account>> listAccount() {
        return Response.good("Found List", accounts.stream().toList());
    }

    private boolean isAnyNameMatchWithGetName (String name) {
        for (Account a : accounts) {
            if (name.equalsIgnoreCase(a.getName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Response<Map<Long, LinkedHashSet<Transaction>>> addNewTransaction(Stack<Transaction> transactions) {
        if (accounts.isEmpty()) return Response.bad("Create account first");

        ref += 1;
        Long debit = 0L;
        Long credit = 0L;

        for (Transaction t : transactions) {
            boolean anyMatchWithAccount = accounts.stream().anyMatch(a -> a.getName().equalsIgnoreCase(t.getAccount()));
            if (!anyMatchWithAccount) return Response.bad("Cannot find the account");

            t.setId(id + 1);
            id = t.getId();
            t.setRef(ref);

            debit += t.getDebit();
            credit += t.getCredit();
        }

        if (!debit.equals(credit)) return Response.bad("Total Debit and Credit must be same");

        LinkedHashSet<Transaction> list = new LinkedHashSet<>(transactions);
        this.transactions.put(ref, list);

        Map<Long, LinkedHashSet<Transaction>> input = new LinkedHashMap<>();
        input.put(ref, list);

        return Response.good("transaction is added", input);
    }

    @Override
    public Response<LinkedHashSet<Transaction>> deleteExistingTransaction(Long ref) {
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                LinkedHashSet<Transaction> deletedList = transactions.get(ref);
                transactions.remove(ref);
                return Response.good(String.format("Transaction with ref: %s is removed", ref), deletedList);
            }
        }

        return Response.bad(String.format("Ref: %s cannot find", ref));
    }

    @Override
    public Response<LinkedHashSet<Transaction>> listTransaction() {
        if (transactions.isEmpty()) return Response.bad("Create transaction first");
        LinkedHashSet<Transaction> list = new LinkedHashSet<>();

        for (LinkedHashSet<Transaction> item : transactions.values()) {
            list.addAll(item);
        }

        return Response.good(String.format("Found %s transaction", list.size()), list);
    }

    @Override
    public Response<LinkedHashSet<Transaction>> getDataTransaction(Long ref) {
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                return Response.good(String.format("Transaction with ref: %s is removed", ref), transactions.get(ref));
            }
        }

        return Response.bad(String.format("Ref: %s cannot find", ref));
    }

    @Override
    public Response<LinkedHashSet<Transaction>> updateExistingTransactionDate(Long ref, Long id, LocalDate newDate) {
        boolean isRef = false;
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                isRef = true;
                break;
            }
        }

        if (!isRef) return Response.bad(String.format("Ref: %s cannot find ", ref));

        for (Transaction item : transactions.get(ref)) {
            if (id.equals(item.getId())) {
                item.setDate(newDate);
                return Response.good(String.format("date transaction with ref: %s and id: %s is updated", ref, id), transactions.get(ref));
            }
        }

        return Response.bad("Cannot find the id: " + id);
    }

    @Override
    public Response<LinkedHashSet<Transaction>> updateExistingTransactionAccount(Long ref, Long id, String newAccount) {
        boolean isRef = false;
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                isRef = true;
                break;
            }
        }

        if (!isRef) return Response.bad(String.format("Ref: %s cannot find ", ref));

        for (Transaction item : transactions.get(ref)) {
            if (id.equals(item.getId())) {
                item.setAccount(newAccount);
                return Response.good(String.format("account transaction with ref: %s and id: %s is updated", ref, id), transactions.get(ref));
            }
        }

        return Response.bad("Cannot find the id: " + id);
    }

    @Override
    public Response<LinkedHashSet<Transaction>> updateExistingTransactionDebit(Long ref, Long id, Long newDebit) {
        boolean isRef = false;
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                isRef = true;
                break;
            }
        }

        if (!isRef) return Response.bad(String.format("Ref: %s cannot find ", ref));

        for (Transaction item : transactions.get(ref)) {
            if (id.equals(item.getId())) {
                item.setDebit(newDebit);
                return Response.good(String.format("debit transaction with ref: %s and id: %s is updated", ref, id), transactions.get(ref));
            }
        }

        return Response.bad("Cannot find the id: " + id);
    }

    @Override
    public Response<LinkedHashSet<Transaction>> updateExistingTransactionCredit(Long ref, Long id, Long newCredit) {
        boolean isRef = false;
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                isRef = true;
                break;
            }
        }

        if (!isRef) return Response.bad(String.format("Ref: %s cannot find ", ref));

        for (Transaction item : transactions.get(ref)) {
            if (id.equals(item.getId())) {
                item.setCredit(newCredit);
                return Response.good(String.format("date transaction with ref: %s and id: %s is updated", ref, id), transactions.get(ref));
            }
        }

        return Response.bad("Cannot find the id: " + id);
    }

    @Override
    public Response<LinkedHashSet<Transaction>> updateExistingTransactionDetail(Long ref, Long id, String newDetail) {
        boolean isRef = false;
        for (Long r : transactions.keySet()) {
            if (ref.equals(r)) {
                isRef = true;
                break;
            }
        }

        if (!isRef) return Response.bad(String.format("Ref: %s cannot find ", ref));

        for (Transaction item : transactions.get(ref)) {
            if (id.equals(item.getId())) {
                item.setDetail(newDetail);
                return Response.good(String.format("detail transaction with ref: %s and id: %s is updated", ref, id), transactions.get(ref));
            }
        }

        return Response.bad("Cannot find the id: " + id);
    }

    public void save () {
        memories.save(id, ref, transactions, accounts);
    }
}