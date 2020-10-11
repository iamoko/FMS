package org.pahappa.systems.views.email;


import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import org.pahappa.systems.core.services.EmailTemplateService;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.EmailTemplate;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import com.googlecode.genericdao.search.Search;

@ManagedBean
@ViewScoped
@ViewPath(path = HyperLinks.TEMPLATEVIEW)
public class EmailTemplateView extends PaginatedTableView<EmailTemplate, EmailTemplateView, EmailTemplateView>{

	/**
	 * 
	 */
	private List<EmailTemplate> filteredTemplate;
	private EmailTemplateService emailTemplateService;
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		this.emailTemplateService = ApplicationContextProvider.getApplicationContext().getBean(EmailTemplateService.class);
		super.setMaximumresultsPerpage(10);
		try {
			reloadFilterReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reloadFromDB(int arg0, int arg1, Map<String, Object> arg2) throws Exception {
		super.setTotalRecords(emailTemplateService.countTempplates());
		super.setDataModels(emailTemplateService.getTemplates());
		
	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EmailTemplate> getFilteredTemplate() {
		return filteredTemplate;
	}

	public void setFilteredTemplate(List<EmailTemplate> filteredTemplate) {
		this.filteredTemplate = filteredTemplate;
	}

	
	
}
