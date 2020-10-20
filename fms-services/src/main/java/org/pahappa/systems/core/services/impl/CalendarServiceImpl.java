package org.pahappa.systems.core.services.impl;

import org.pahappa.systems.core.dao.CalendarDao;
import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Calendar;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CalendarServiceImpl implements CalendarService {
	
	@Autowired
	CalendarDao calendarDao;

	@Override
	public List<Calendar> getDates() {
		Search search = new Search();
		search.addSortDesc("dateCreated");
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return calendarDao.search(search);
	}

	@Override
	public void setDate(Calendar calendar) {
		calendar.setRecordStatus(RecordStatus.ACTIVE);
		calendarDao.save(calendar);
	}

	@Override
	public List<Calendar> getCalendar(Search search) {
		search.addSortDesc("dateCreated");
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return calendarDao.search(search);
	}

	@Override
	public void deleteCalendar(Calendar calendar) {
		calendar.setRecordStatus(RecordStatus.DELETED);
		calendarDao.save(calendar);
	}

	@Override
	public int countHolidays() {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return calendarDao.count(search);
	}

	@Override
	public List<Calendar> getCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit, int offSet) {
		Search search = CustomSearchUtils.genereateSearchObjectForHolidays(searchTerm, startDate, endDate, sortField);
		search.setFirstResult(offSet);
		search.setMaxResults(limit);
		search.addSortDesc("dateCreated");
		return calendarDao.search(search);
	}

	@Override
	public int countCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField) {
		Search search = CustomSearchUtils.genereateSearchObjectForHolidays(searchTerm, startDate, endDate, sortField);
		return calendarDao.count(search);
	}
}
