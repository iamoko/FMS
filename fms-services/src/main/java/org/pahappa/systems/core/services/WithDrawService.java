package org.pahappa.systems.core.services;


import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.Withdraw;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;

import com.googlecode.genericdao.search.Search;

public interface WithDrawService {
	/*
	Generate a transaction number
			*/
	String generateTransactionNo();
	/*
	 * Retrieve all the public holidays
	 */
    List<Withdraw> getTransactions();

	/*
	 * Insert The public holidays
	 */
    void save(Withdraw transactions);
	/*
	 * Get a specific calendar
	 */
    List<Withdraw> getTransactions(Search search);
	/*
	 * Delete Event
	 */
    void deleteTransaction(Withdraw withdraw);
	/*
	 * Total Number of holidays
	 */
    int countWithdraws();

	/*
	 * Count Withdraws by user
	 */
    int countWithdrawsByUser(User user);
	/*
	 * Get Transactions by user
	 */
    
    List<Withdraw> getTransactionsByUser(User user);
    
    List<Withdraw> getUserWithdraws(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit, int offSet);
    
    int countSearchedWithdraws(String searchTerm, Date startDate, Date endDate, SortField sortField);
}
