/**
 * 
 */
package org.pahappa.systems.views;

import org.pahappa.systems.core.services.UserLogService;
import org.pahappa.systems.models.UserLog;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

/**
 * @author Eng.Ivan
 *
 */
public class StoreLogs {
	
	
	public static void Log(String log) {
		UserLog userLog = new UserLog();
		UserLogService logService = ApplicationContextProvider.getBean(UserLogService.class);
		userLog.setAction(log);
		User user= SharedAppData.getLoggedInUser();
		try {
			logService.save(user, log);
		} catch (ValidationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
