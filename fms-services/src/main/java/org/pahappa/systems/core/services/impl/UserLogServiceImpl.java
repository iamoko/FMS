package org.pahappa.systems.core.services.impl;

import java.util.Date;
import java.util.List;

import org.pahappa.systems.core.dao.UserLogDao;
import org.pahappa.systems.core.services.UserLogService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.UserLog;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

@Service
@Transactional
public class UserLogServiceImpl implements UserLogService {

	@Autowired
	UserLogDao userLogDao;

	@Override
	public void save(UserLog userLog) throws ValidationFailedException {

		userLog.setdateLogged(new Date());

		userLogDao.save(userLog);

	}

	@Override
	public void save(String action) throws ValidationFailedException {
		UserLog userLog = new UserLog();
		userLog.setUser(SharedAppData.getLoggedInUser());
		userLog.setAction(action);
		userLog.setdateLogged(new Date());

		userLogDao.save(userLog);

	}

	@Override
	public void save(User user, String action) throws ValidationFailedException {
		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setAction(action);
		userLog.setdateLogged(new Date());
		userLog.setRecordStatus(RecordStatus.ACTIVE);
		userLogDao.save(userLog);

	}

	@Override
	public List<UserLog> getUserLogs(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit,
			int offSet) {
		Search search = CustomSearchUtils.genereateSearchObjectForLogs(searchTerm, startDate, endDate, sortField);
		search.setFirstResult(offSet);
		search.setMaxResults(limit);
		search.addSortDesc("dateCreated");
		return userLogDao.search(search);
	}

	@Override
	public int countUserLogs(String searchTerm, Date startDate, Date endDate, SortField sortField) {
		Search search = CustomSearchUtils.genereateSearchObjectForLogs(searchTerm, startDate, endDate, sortField);

		return userLogDao.count(search);
	}

}
