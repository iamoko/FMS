package org.pahappa.systems.core.services;

import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.models.UserLog;
import org.pahappa.systems.models.Withdraw;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;

import com.googlecode.genericdao.search.Search;

public interface DepositService {
	/*
	 * Retrieve all the public holidays
	 */
	List<Deposit> getTransactions();

	/*
	 * Insert The public holidays
	 */
	void save(Deposit deposit);

	/*
	 * Get a specific calendar
	 */
	List<Deposit> getTransactions(Search search);

	/*
	 * Delete Event
	 */
	void deleteTransaction(Deposit deposit);

	/*
	 * Total Number of holidays
	 */
	int countDeposits();
	/*
	 * Get Transactions by user
	 */

	List<Deposit> getTransactionsByUser(User user);

	List<Deposit> getUserDeposits(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit,
                                  int offSet);

	int countSearchedDeposits(String searchTerm, Date startDate, Date endDate, SortField sortField);
}
