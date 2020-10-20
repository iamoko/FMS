package org.pahappa.systems.views.home;

import javax.faces.bean.ManagedBean;

import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.faces.bean.*;

@ManagedBean(name = "calendarForm")
@SessionScoped
@ViewPath(path = HyperLinks.CALENDARFORM)
public class CalendarForm extends WebFormView<Calendar, CalendarForm, CalendarView> {

	/**
	 * Handles the failed operations to be shown to the user
	 */
	private CalendarService calendarService;
	private static final long serialVersionUID = 1L;
	/*
	 * Handle Days Calculations
	 */

	@Override
	public void beanInit() {
		resetModal();
		this.calendarService = ApplicationContextProvider.getBean(CalendarService.class);

	}

	@Override
	public void pageLoadInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void persist() {

		try {
			StoreLogs.Log("Saved new Holiday on " + super.model.getComment() + " from " + super.model.getStartDate()
					+ " to " + super.model.getEndDate());

			this.calendarService.setDate(super.model);
			MessageComposer.Compose("Action Successful", super.model.getComment()+" Holiday Created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetModal() {
		super.resetModal();
		super.model = new Calendar();
	}

}
