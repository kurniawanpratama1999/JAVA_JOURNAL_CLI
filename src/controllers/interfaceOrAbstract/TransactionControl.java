package controllers.interfaceOrAbstract;

import models.Transaction;
import utils.Response;

import java.time.LocalDate;
import java.util.*;

public interface TransactionControl {

    /* int id, LocalDate date, String account, Long debit, Long credit, String detail */
    Response<Map<Long, LinkedHashSet<Transaction>>> addNewTransaction(Stack<Transaction> transactions);
    Response<LinkedHashSet<Transaction>> deleteExistingTransaction(Long ref);
    Response<LinkedHashSet<Transaction>> listTransaction();
    Response<LinkedHashSet<Transaction>> getDataTransaction(Long ref);
    Response<LinkedHashSet<Transaction>> updateExistingTransactionDate(Long ref, Long id, LocalDate newDate);
    Response<LinkedHashSet<Transaction>> updateExistingTransactionAccount(Long ref, Long id, String newAccount);
    Response<LinkedHashSet<Transaction>> updateExistingTransactionDebit(Long ref, Long id, Long newDebit);
    Response<LinkedHashSet<Transaction>> updateExistingTransactionCredit(Long ref, Long id, Long newCredit);
    Response<LinkedHashSet<Transaction>> updateExistingTransactionDetail(Long ref, Long id, String newDetail);

}