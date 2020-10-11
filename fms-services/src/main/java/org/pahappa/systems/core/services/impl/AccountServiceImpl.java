package org.pahappa.systems.core.services.impl;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

import org.pahappa.systems.core.dao.AccountDao;
import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.models.Account;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public void save(Account account) {
        account.setRecordStatus(RecordStatus.ACTIVE);
        this.accountDao.save(account);
    }


    @Override
    public int countAccounts(Search search) {
        return accountDao.count(search);
    }

    @Override
    public Account createAccount(Account account) {
        account.setRecordStatus(RecordStatus.ACTIVE);
        return this.accountDao.save(account);
    }

    @Override
    public void delete(Account account) {
        account.setRecordStatus(RecordStatus.DELETED);
        this.accountDao.save(account);
    }

    @Override
    public List<Account> getAccounts(Search searchTerm, int offset, int limit) {
        searchTerm.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        searchTerm.setFirstResult(offset);
        searchTerm.setMaxResults(limit);
        return this.accountDao.search(searchTerm);
    }

    @Override
    public Account getAccountById(String id) {
        return this.accountDao.find(id);
    }

    @Override
    public Account getAccountByUser(User user) {
        return accountDao.searchUniqueByPropertyEqual("user", user);
    }

    @Override
    public List<Account> getAccountsByRoles(String role) {

        Search search = new Search();
        SearchResult<Account> accounts = accountDao.searchAndCount(search);
        return accounts.getResult();
    }

    @Override
    public List<User> getUsersByRoles(String role) {

        List<User> users = new ArrayList<>();
        List<Account> accounts = getAccountsByRoles(role);

        for (Account account : accounts) {
            users.add(account.getUser());
        }
        return users;
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        Search search = new Search();
        search.addFilterEqual("user", user);
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return accountDao.search(search);
    }

    @Override
    public List<Account> getAccounts() {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return accountDao.search(search);
    }

}
