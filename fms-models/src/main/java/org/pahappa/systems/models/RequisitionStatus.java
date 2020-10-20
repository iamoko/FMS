package org.pahappa.systems.models;

public enum RequisitionStatus {

	PENDING("Pending"), 
	APPROVED("Approved"),  
	ACKNOWLEDGED("Acknowledged"), 
	DECLINED("Declined");
	
	public String status;
	RequisitionStatus(String status) {
		this.status = status;
	}
}
