package org.pahappa.systems.core.services;


import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.UserLog;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;


public interface UserLogService {
	
    void save(UserLog userLog) throws ValidationFailedException;
    
    void save(String action) throws ValidationFailedException;
    
    void save(User user, String action) throws ValidationFailedException;
    
    List<UserLog> getUserLogs(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit, int offSet);
   
    int countUserLogs(String searchTerm, Date startDate, Date endDate, SortField sortField);

    
}
