package org.pahappa.systems.views.home;


import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.core.services.NonWorkingDaysService;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.NonWorkingDays;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
@ViewPath ( path=HyperLinks.USERDAYS)
public class NonWorkingDaysView extends PaginatedTableView<NonWorkingDays, NonWorkingDaysView, NonWorkingDaysView>{

	/**
	 * 
	 */
	private List<NonWorkingDays> filteredCalendar;
	private int totalNum;
	private NonWorkingDaysService nonWorkingDaysService;
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		this.nonWorkingDaysService = ApplicationContextProvider.getApplicationContext().getBean(NonWorkingDaysService.class);
		this.filteredCalendar = nonWorkingDaysService.getDates();
		this.totalNum = nonWorkingDaysService.countHolidays();
	}
	
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reloadFromDB(int arg0, int arg1, Map<String, Object> arg2) throws Exception {
		super.setTotalRecords(nonWorkingDaysService.countHolidays());
		super.setDataModels(nonWorkingDaysService.getDates());
	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<NonWorkingDays> getFilteredCalendar() {
		return filteredCalendar;
	}

	public void setFilteredCalendar(List<NonWorkingDays> filteredCalendar) {
		this.filteredCalendar = filteredCalendar;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	@Override
	public void reloadFilterReset() {
		super.setTotalRecords(this.nonWorkingDaysService.countHolidays());

        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	public void delete(NonWorkingDays nonWorkingDays) {
		
		try {
			this.nonWorkingDaysService.deleteCalendar(nonWorkingDays);
			super.reloadFilterReset();
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(((HttpServletRequest) externalContext.getRequest()).getRequestURI());
//			UiUtils.showMessageBox(calendar.getComment() + " has been deleted", "Action Successful", RequestContext.getCurrentInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
