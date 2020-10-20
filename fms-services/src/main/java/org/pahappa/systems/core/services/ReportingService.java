/**
 * 
 */
package org.pahappa.systems.core.services;

import java.util.Map;

import org.sers.webutils.model.security.User;

/**
 * @author Eng.Ivan
 *
 */
public interface ReportingService {
	Map<String, Integer> getUserRequisitionSummary(User user);
}
