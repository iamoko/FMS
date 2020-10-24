package org.pahappa.systems.core.services;


import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.NonWorkingDays;
import org.sers.webutils.model.security.User;

import java.util.List;

public interface NonWorkingDaysService {
	/*
	 * Retrieve all the public holidays
	 */
    List<NonWorkingDays> getDates();

	/*
	 * Insert The public holidays
	 */
    void setDate(NonWorkingDays nonWorkingDays);

	/*
	 * Get a specific calendar
	 */
    List<NonWorkingDays> getCalendar(User user);
	/*
	 * Delete Event
	 */
    void deleteCalendar(NonWorkingDays nonWorkingDays);
	/*
	 * Total Number of holidays
	 */
    int countHolidays();
}
