/**
 * 
 */
package org.pahappa.systems.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.pahappa.systems.core.dao.RequistionDao;
import org.pahappa.systems.core.services.ReportingService;
import org.pahappa.systems.models.RequisitionStatus;
import org.sers.webutils.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author Eng.Ivan
 *
 */
@Service
@Transactional(readOnly = true)
public class ReportingServiceImp implements ReportingService {

	@Autowired
	RequistionDao requisitionDao;

	@Override
	public Map<String, Integer> getUserRequisitionSummary(User user) {
		Map<String, Integer> values = new HashMap<String, Integer>();
		values.put("Approved", countRequisitions(user, RequisitionStatus.APPROVED));
		values.put("Rejected", countRequisitions(user, RequisitionStatus.DECLINED));
		values.put("Pending", countRequisitions(user, RequisitionStatus.PENDING));
		values.put("Acknowledged", countRequisitions(user, RequisitionStatus.ACKNOWLEDGED));

		return values;
	}

	private int countRequisitions(User user, RequisitionStatus requisitionStatus) {
		/*
		 * Search search = new Search(); search.addFilterEqual("createdBy",
		 * user).addFilterEqual("requisitionStatus", requisitionStatus);
		 */
		/*
		 * Query the DB data from here
		 */
//		return requisitionDao.count(search);
		return (int) (Math.random() * (100 - 50 + 1) + 50);
	}
}
