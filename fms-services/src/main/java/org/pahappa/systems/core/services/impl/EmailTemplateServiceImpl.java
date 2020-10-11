package org.pahappa.systems.core.services.impl;

import org.pahappa.systems.core.dao.EmailDao;
import org.pahappa.systems.core.services.EmailTemplateService;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.EmailTemplate;
import org.pahappa.systems.models.RequisitionStatus;
import org.sers.webutils.model.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import java.util.List;

@Service
@Transactional
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	EmailDao emailDao;
	@Override
	public List<EmailTemplate> getTemplates() {
		return emailDao.findAll();
	}

	@Override
	public void save(EmailTemplate emailTemplate) {
		emailTemplate.setRecordStatus(RecordStatus.ACTIVE);
		emailDao.save(emailTemplate);
		
	}

	@Override
	public List<Calendar> getTemplates(Search search) {
		search.addSortDesc("dateCreated");
		return emailDao.search(search);
	}

	@Override
	public void deleteTemplate(EmailTemplate emailTemplate) {
		emailDao.delete(emailTemplate);
		
	}

	@Override
	public int countTempplates() {
		Search search = new Search();
		return emailDao.count(search);
	}

	@Override
	public EmailTemplate getTemplateByStatus(RequisitionStatus requisitionStatus) {
		return emailDao.searchUniqueByPropertyEqual("requisitionStatus", requisitionStatus);
	}
	
	
	
}
