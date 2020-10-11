package org.pahappa.systems.core.services;


import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.Calendar;

import com.googlecode.genericdao.search.Search;
import org.sers.webutils.model.utils.SortField;

public interface CalendarService {
	/*
	 * Retrieve all the public holidays
	 */
    List<Calendar> getDates();

	/*
	 * Insert The public holidays
	 */
    void setDate(Calendar calendar);

	/*
	 * Get a specific calendar
	 */
    List<Calendar> getCalendar(Search search);
	/*
	 * Delete Event
	 */
    void deleteCalendar(Calendar calendar);
	/*
	 * Total Number of holidays
	 */
    int countHolidays();

    List<Calendar> getCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit,
									int offSet);

	int countCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField);
}
