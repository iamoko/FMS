package org.pahappa.systems.views.email;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.EmailService;
import org.pahappa.systems.models.Email;
import org.pahappa.systems.views.home.HomePage;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name="emailsView")
@SessionScoped
@ViewPath(path = HyperLinks.LOGSVIEW)
public class EmailsView  extends PaginatedTableView<Email, EmailsView, HomePage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EmailService emailService;
	
	private Date startDate;
	private Date endDate;
	private String searchTerm;
	private SortField sortField;

	/**
	 * Run this on bean initialization
	 */
	@PostConstruct
	public void init() {
		
		this.emailService = ApplicationContextProvider.getBean(EmailService.class);
		super.setMaximumresultsPerpage(10);
		try {
			reloadFilterReset();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	
	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
		this.setDataModels(this.emailService.getEmails(searchTerm, this.startDate, this.endDate, this.sortField, limit, offset));
		
	}
	
	
  @Override
	public void reloadFilterReset() throws Exception {
		this.setTotalRecords(this.emailService.countEmails(searchTerm, this.startDate, this.endDate, this.sortField));
		super.reloadFilterReset();
	}
	
	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}


	public SortField getSortField() {
		return sortField;
	}


	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}

}
