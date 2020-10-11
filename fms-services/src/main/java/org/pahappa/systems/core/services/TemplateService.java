package org.pahappa.systems.core.services;

import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.Template;
import org.pahappa.systems.models.TemplateType;

import java.util.List;

public interface TemplateService {
	List<Template> updateTemplates(List<Template> templates);

	Template updateTemplate(Template template);

	List<Template> getTemplates();

	Template getTemplate(TemplateType constants);

	String makeMessage(TemplateType template, Account account, Requisition requisition, String pwd);

	String populateMessage(String message, Account account, Requisition requisition, String pwd);
}
