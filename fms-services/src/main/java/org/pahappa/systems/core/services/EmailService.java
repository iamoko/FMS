package org.pahappa.systems.core.services;

import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.Email;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;

import com.googlecode.genericdao.search.Search;

public interface EmailService {
    void save(User user, String heading, String message);

    int countLocalDates(Search searchTerm);

    public List<Email> getEmails(String searchTerm, Date startDate, Date endDate, SortField sortField, int limit, int offSet);

    int countEmails(String searchTerm, Date startDate, Date endDate, SortField sortField);


}
