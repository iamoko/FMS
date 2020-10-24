package org.pahappa.systems.core.services.impl;

import java.util.Date;
import java.util.List;

import org.pahappa.systems.core.dao.impl.EmailLogDao;
import org.pahappa.systems.core.services.EmailService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Email;
import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;


@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailLogDao emailDao;


    @Override
    public void save(User user, String heading, String message) {
        Email email = new Email();
        email.setUser(user);
        email.setMessage(message);
        email.setHeading(heading);
        email.setRecordStatus(RecordStatus.ACTIVE);
        emailDao.save(email);

    }

    @Override
    public int countLocalDates(Search searchTerm) {
        return emailDao.count(searchTerm);
    }

    @Override
    public List<Email> getEmails(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit,
                                 int offSet) {
        Search search = CustomSearchUtils.genereateSearchObjectForEmails(searchTerm, startDate, endDate, sortField);
        search.setFirstResult(offSet);
        search.setMaxResults(limit);
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            search.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        return emailDao.search(search);
    }

    @Override
    public int countEmails(String searchTerm, Date startDate, Date endDate, SortField sortField) {
        Search search = CustomSearchUtils.genereateSearchObjectForEmails(searchTerm, startDate, endDate, sortField);
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            search.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        return emailDao.count(search);
    }


}
