package org.pahappa.systems.core.services.impl;

import org.pahappa.systems.core.dao.WithdrawDao;
import org.pahappa.systems.core.services.WithDrawService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Withdraw;
import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WithdrawServiceImpl implements WithDrawService {

	@Autowired
	WithdrawDao withdrawDao;

	@Override
	public void save(Withdraw withdraw) {
		withdraw.setRecordStatus(RecordStatus.ACTIVE);
		withdrawDao.save(withdraw);
	}

	@Override
	public List<Withdraw> getTransactions(Search search) {
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
			search.addFilterEqual("user", SharedAppData.getLoggedInUser());
		}
		search.addSortDesc("dateCreated");
		return withdrawDao.search(search);
	}

	@Override
	public void deleteTransaction(Withdraw withdraw) {
		withdraw.setRecordStatus(RecordStatus.DELETED);
		withdrawDao.save(withdraw);

	}

	@Override
	public int countWithdraws() {
		Search search = new Search();
		return withdrawDao.count(search);
	}

	@Override
	public List<Withdraw> getTransactionsByUser(User user) {
		Search search = new Search();
		search.addFilterEqual("user", user);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		search.addSortDesc("dateCreated");
		return withdrawDao.search(search);
	}

	@Override
	public String generateTransactionNo() {
		Search search = new Search();
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		StringBuilder build = new StringBuilder();
		build.append(SharedAppData.getLoggedInUser().getFirstName().toUpperCase().charAt(0));
		build.append(SharedAppData.getLoggedInUser().getLastName().toUpperCase().charAt(0));

		String trans_no = build.toString();
		return trans_no + "/" + dateFormat.format(date) + "/" + (withdrawDao.count(search) + 1);
	}

	@Override
	public List<Withdraw> getTransactions() {
		Search search = new Search();
		if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
			search.addFilterEqual("user", SharedAppData.getLoggedInUser());
		}
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		search.addSortDesc("dateCreated");
		return withdrawDao.search(search);
	}

	@Override
	public List<Withdraw> getUserWithdraws(String searchTerm, Date startDate, Date endDate, SortField sortField,
			int limit, int offSet) {
		Search search = CustomSearchUtils.genereateSearchObjectForWithdraws(searchTerm, startDate, endDate, sortField);
		search.setFirstResult(offSet);
		search.setMaxResults(limit);
		if(!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_WITHDRAW)){
			User user = SharedAppData.getLoggedInUser();
			search.addFilterEqual("user", user);
		}
		search.addSortDesc("dateCreated");
		return withdrawDao.search(search);
	}

	@Override
	public int countSearchedWithdraws(String searchTerm, Date startDate, Date endDate, SortField sortField) {
		Search search = CustomSearchUtils.genereateSearchObjectForWithdraws(searchTerm, startDate, endDate, sortField);
		if(!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_WITHDRAW)){
			User user = SharedAppData.getLoggedInUser();
			search.addFilterEqual("user", user);
		}
		return withdrawDao.count(search);
	}

	@Override
	public int countWithdrawsByUser(User user) {
		Search search = new Search();
		search.addFilterEqual("user", user);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return withdrawDao.count(search);
	}

}
