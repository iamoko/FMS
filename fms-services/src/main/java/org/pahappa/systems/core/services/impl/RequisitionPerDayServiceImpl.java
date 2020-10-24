package org.pahappa.systems.core.services.impl;

import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.core.dao.CalendarDao;
import org.pahappa.systems.core.dao.RequistionPerDayDao;
import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.core.services.RequisitionPerDayService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.RequisitionPerDay;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RequisitionPerDayServiceImpl implements RequisitionPerDayService {
	
	@Autowired
	RequistionPerDayDao requistionPerDayDao;


	@Override
	public List<RequisitionPerDay> getDates() {
		Search search = new Search();
		search.addSortDesc("dateCreated");
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return requistionPerDayDao.search(search);
	}

	@Override
	public void setDate(RequisitionPerDay requisitionPerDay) {
		requisitionPerDay.setRecordStatus(RecordStatus.ACTIVE);
		requistionPerDayDao.save(requisitionPerDay);
	}

	@Override
	public List<RequisitionPerDay> getCalendar(Search search) {
		search.addFilterEqual("user", SharedAppData.getLoggedInUser());
		search.addSortDesc("dateCreated");
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return requistionPerDayDao.search(search);
	}

	@Override
	public void deleteCalendar(RequisitionPerDay calendar) {

	}

	@Override
	public int countHolidays() {
		return 0;
	}

	@Override
	public List<RequisitionPerDay> getCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit, int offSet) {
		return null;
	}

	@Override
	public int countCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField) {
		return 0;
	}
}
