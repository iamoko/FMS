/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pahappa.systems.core.services;


import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.sers.webutils.model.security.User;

import com.googlecode.genericdao.search.Search;

/**
 * @author Eng.Ivan
 *
 */

public interface RequisitionService {

	/*
	 * Save the requisition
	 */
	void save(Requisition requisition) throws Exception;

	/*
	 * Approve or decline a User Requisition
	 */
	void updateStatus(RequisitionStatus status, Requisition requisition);

	/*
	 * Acknowledge Requisition
	 */
	void acknowledge(Requisition requisition);

	/*
	 * Get all requisitions
	 */
	List<Requisition> getRequisitions(Search searchTerm, int offset, int limit);

	/*
	 * Get Specific Requisition
	 */
	Requisition getRequisitionById(String id);

	/*
	 * Get a specified group of requisitions, approved, declined or pending
	 */
	List<Requisition> getRequisitionByStatus(RequisitionStatus requisitionStatus);
	
	/*
	 * Get all requisitions, approved, declined or pending
	 */
	List<Requisition> getRequisitions();
	/*
	 * Delete requisition
	 */
	void deleteRequisition(Requisition requisition);

	/*
	 * Count the number of requisitions when searched
	 */
	public int countRequisitions(Search searchTerm);
	
	/*
	 * Count the number of requisitions by user when searched
	 */
	public int countRequisitionsByUser(Search searchTerm, User user);
	
	
	public List<Requisition> getRequisitionsByUserId(User user);
	/*
	 * Change the requisition Status
	 */
	void changeRequisitionStatus(Account account, Requisition requisition, RequisitionStatus requisitionStatus, String comment);

	/*
	 * Get count of requisitions by status
	 */
	int countRequisitionsByStatus(RequisitionStatus requisitionStatus);
	
	int getDifferenceDays(Date d1, Date d2);
	
	void setViewedRequisition(Requisition requisition);


	int countReq(Search search);
	
	
	
}
