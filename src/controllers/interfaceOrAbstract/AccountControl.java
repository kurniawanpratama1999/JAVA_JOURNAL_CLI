package controllers.interfaceOrAbstract;

import models.Account;
import utils.Response;

import java.util.Collection;

public interface AccountControl {

    Response<Account> addNewAccount(String name);
    Response<Account> deleteExistingAccountName(String name);
    Response<Account> updateExistingAccountName(String name, String newName);
    Response<Collection<Account>> listAccount();

}
