package org.pahappa.systems.views.email;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.pahappa.systems.core.services.EmailTemplateService;
import org.pahappa.systems.models.EmailTemplate;
import org.pahappa.systems.models.RequisitionStatus;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.primefaces.PrimeFaces;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.*;

@ManagedBean(name = "emailTemplateForm")
@SessionScoped
@ViewPath(path = HyperLinks.TEMPLATEFORM)
public class EmailTemplateForm extends WebFormView<EmailTemplate, EmailTemplateForm, EmailTemplateView> {

	/**
	 * Handles the failed operations to be shown to the user
	 */
	private EmailTemplateService emailTemplateService;
	private static final long serialVersionUID = 1L;
	
	
	private Map<String, RequisitionStatus> emailtemplates = new HashMap<String, RequisitionStatus>();
	/*
	 * Handle Days Calculations
	 */
	

	@Override
	public void beanInit() {
		resetModal();
		this.emailTemplateService = ApplicationContextProvider.getBean(EmailTemplateService.class);
		
		//cities
		emailtemplates = new HashMap<String, RequisitionStatus>();
		emailtemplates.put(RequisitionStatus.APPROVED.name() , RequisitionStatus.APPROVED);
		emailtemplates.put(RequisitionStatus.ACKNOWLEDGED.name(), RequisitionStatus.ACKNOWLEDGED);
		emailtemplates.put(RequisitionStatus.DECLINED.name(), RequisitionStatus.DECLINED);
        
	}

	@Override
	public void pageLoadInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void persist() {

		try {
			if(emailTemplateService.getTemplateByStatus(super.model.getRequisitionStatus()) != null) {
				StoreLogs.Log("Failed to create, Email Template"+super.model.getRequisitionStatus()+"Already Exists");
				MessageComposer.Failed("Action Failed",super.model.getRequisitionStatus()+"Template for the Status already Exists, Please Update it instead");
			}else {

				MessageComposer.Compose("Action Successful",super.model.getRequisitionStatus()+" Email Template has been created");
				StoreLogs.Log("New Email Template Created for "+super.model.getRequisitionStatus());
				this.emailTemplateService.save(super.model);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public void resetModal() {
		super.resetModal();
		super.model = new EmailTemplate();
	}

	/**
	 * @return the emailtemplates
	 */
	public Map<String, RequisitionStatus> getEmailtemplates() {
		return emailtemplates;
	}

	/**
	 * @param emailtemplates the emailtemplates to set
	 */
	public void setEmailtemplates(Map<String, RequisitionStatus> emailtemplates) {
		this.emailtemplates = emailtemplates;
	}

	



}
