package org.pahappa.systems.core.services.impl;

import com.googlecode.genericdao.search.Search;

import org.pahappa.systems.core.dao.TemplateDao;
import org.pahappa.systems.core.services.TemplateService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.Template;
import org.pahappa.systems.models.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
	@Autowired
	TemplateDao templateDao;

	@Override
	public List<Template> updateTemplates(List<Template> templates) {
		return null;
	}

	@Override
	public Template updateTemplate(Template template) {

		Search search = new Search();
		search.addFilterEqual("template", template.getTemplate());
		Template savedTemplate = this.templateDao.searchUnique(search);

		if (savedTemplate == null)
			return this.templateDao.save(template);

		savedTemplate.setValue(template.getValue());

		return this.templateDao.save(savedTemplate);
	}

	@Override
	public List<Template> getTemplates() {
		return this.templateDao.findAll();
	}

	@Override
	public Template getTemplate(TemplateType template) {
		Search search = new Search();
		search.addFilterEqual("template", template);
		return this.templateDao.searchUnique(search);
	}

	@Override
	public String makeMessage(TemplateType templateType, Account account, Requisition requisition, String pwd) {

		Template template = this.getTemplate(templateType);

		if (template == null)
			return "";


		String message = template.getValue();

		return this.populateMessage(message, account, requisition, pwd);
	}

	@Override
	public Template getApprovedTemplateValue(TemplateType template) {
		return null;
	}

	@Override
	public String populateMessage(String message, Account account, Requisition requisition, String pwd) {
		Search search = new Search();
		search.addFilterEqual("template", TemplateType.APPROVED);
		return this.templateDao.searchUnique(search);
	}

}
