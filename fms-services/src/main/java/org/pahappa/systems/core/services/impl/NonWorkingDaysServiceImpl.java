package org.pahappa.systems.core.services.impl;

import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.core.dao.NonWorkingDaysDao;
import org.pahappa.systems.core.services.NonWorkingDaysService;
import org.pahappa.systems.models.NonWorkingDays;
import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NonWorkingDaysServiceImpl implements NonWorkingDaysService {

    @Autowired
    NonWorkingDaysDao nonWorkingDaysDao;

    @Override
    public List<NonWorkingDays> getDates() {
        Search search = new Search();
        search.addSortDesc("dateCreated");
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            search.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return nonWorkingDaysDao.search(search);
    }

    @Override
    public void setDate(NonWorkingDays nonWorkingDays) {
        nonWorkingDays.setRecordStatus(RecordStatus.ACTIVE);
        nonWorkingDaysDao.save(nonWorkingDays);
    }

    @Override
    public List<NonWorkingDays> getCalendar(User user) {
        Search search = new Search();
        search.addFilterEqual("user", user);
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return nonWorkingDaysDao.search(search);
    }

    @Override
    public void deleteCalendar(NonWorkingDays nonWorkingDays) {
        nonWorkingDays.setRecordStatus(RecordStatus.DELETED);
        nonWorkingDaysDao.save(nonWorkingDays);
    }

    @Override
    public int countHolidays() {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return nonWorkingDaysDao.count(search);
    }


}
