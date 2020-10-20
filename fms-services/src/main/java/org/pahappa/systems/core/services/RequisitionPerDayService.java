package org.pahappa.systems.core.services;


import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.models.RequisitionPerDay;
import org.sers.webutils.model.utils.SortField;

import java.util.Date;
import java.util.List;

public interface RequisitionPerDayService {
	/*
	 * Retrieve all the public holidays
	 */
    List<RequisitionPerDay> getDates();

	/*
	 * Insert The public holidays
	 */
    void setDate(RequisitionPerDay requisitionPerDay);

	/*
	 * Get a specific calendar
	 */
    List<RequisitionPerDay> getCalendar(Search search);
	/*
	 * Delete Event
	 */
    void deleteCalendar(RequisitionPerDay calendar);
	/*
	 * Total Number of holidays
	 */
    int countHolidays();

    List<RequisitionPerDay> getCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit,
											 int offSet);

	int countCalendarDates(String searchTerm, Date startDate, Date endDate, SortField sortField);
}
