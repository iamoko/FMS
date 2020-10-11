/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pahappa.systems.core.services;

import java.util.List;
import org.pahappa.systems.models.Account;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;

import com.googlecode.genericdao.search.Search;

public interface AccountService {

	User createUser(User user);

	void save(Account account);

	int countAccounts(Search search);

	Account createAccount(Account account);

	void delete(Account account);

	List<Account> getAccounts();

	List<Account> getAccounts(Search searchTerm, int offset, int limit);

	Account getAccountById(String id);

	Account getAccountByUser(User user);

	List<Account> getAccountsByRoles(String role);

	List<User> getUsersByRoles(String role);

	List<Account> getAccountsByUser(User user);
}
