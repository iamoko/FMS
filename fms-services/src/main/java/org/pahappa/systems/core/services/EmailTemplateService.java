package org.pahappa.systems.core.services;


import java.util.List;

import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.EmailTemplate;
import org.pahappa.systems.models.RequisitionStatus;

import com.googlecode.genericdao.search.Search;

public interface EmailTemplateService {
	/*
	 * Retrieve all the public holidays
	 */
    List<EmailTemplate> getTemplates();

	/*
	 * Insert The public holidays
	 */
    void save(EmailTemplate emailTemplate);

	/*
	 * Get a specific calendar
	 */
    List<Calendar> getTemplates(Search search);
	/*
	 * Delete Event
	 */
    void deleteTemplate(EmailTemplate emailTemplate);
	/*
	 * Total Number of holidays
	 */
    int countTempplates();
	/*
	 * Get email template by requisition status
	 */
    
    EmailTemplate getTemplateByStatus(RequisitionStatus requisitionStatus);
}
