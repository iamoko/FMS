package org.pahappa.systems.core.services.impl;

import org.pahappa.systems.core.dao.DepositDao;
import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

	@Autowired
	DepositDao depositDao;

	@Override
	public void save(Deposit deposit) {
		deposit.setRecordStatus(RecordStatus.ACTIVE);
		depositDao.save(deposit);

	}

	@Override
	public List<Deposit> getTransactions(Search search) {
		search.addSortDesc("dateCreated");
		if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
			search.addFilterEqual("user", SharedAppData.getLoggedInUser());
		}
		return depositDao.search(search);
	}

	@Override
	public void deleteTransaction(Deposit Deposit) {
		depositDao.delete(Deposit);

	}

	@Override
	public int countDeposits() {
		Search search = new Search();
		return depositDao.count(search);
	}

	@Override
	public List<Deposit> getTransactionsByUser(User user) {
		Search search = new Search();
		search.addSortDesc("dateCreated");
		search.addFilterEqual("user", user);
		return depositDao.search(search);
	}

	@Override
	public List<Deposit> getTransactions() {

		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
			search.addFilterEqual("user", SharedAppData.getLoggedInUser());
		}
		search.addSortDesc("dateCreated");
		return this.depositDao.search(search);
	}

	@Override
	public List<Deposit> getUserDeposits(String searchTerm, Date startDate, Date endDate, SortField sortField,
			int limit, int offSet) {

		Search search = CustomSearchUtils.genereateSearchObjectForDeposits(searchTerm, startDate, endDate, sortField);
		search.setFirstResult(offSet);
		search.setMaxResults(limit);
		search.addSortDesc("dateCreated");
		return depositDao.search(search);
	}

	@Override
	public int countSearchedDeposits(String searchTerm, Date startDate, Date endDate, SortField sortField) {
		Search search = CustomSearchUtils.genereateSearchObjectForWithdraws(searchTerm, startDate, endDate, sortField);
		if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
			search.addFilterEqual("user", SharedAppData.getLoggedInUser());
		}
		return depositDao.count(search);
	}

}
