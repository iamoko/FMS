package org.pahappa.systems.models;

import javax.persistence.*;
import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "email_template")
public class EmailTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String template;
	private RequisitionStatus requisitionStatus;
	

	/**
	 * @return the requisitionStatus
	 */
	@Column(name = "requisitionStatus")
	public RequisitionStatus getRequisitionStatus() {
		return requisitionStatus;
	}

	/**
	 * @param requisitionStatus the requisitionStatus to set
	 */
	public void setRequisitionStatus(RequisitionStatus requisitionStatus) {
		this.requisitionStatus = requisitionStatus;
	}


	@Column(name = "template", length = 2000)
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "EmailTemplate [template=" + template + ", requisitionStatus=" + requisitionStatus + "]";
	}

	
}
