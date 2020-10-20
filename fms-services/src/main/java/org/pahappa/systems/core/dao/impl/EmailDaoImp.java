package org.pahappa.systems.core.dao.impl;

import org.pahappa.systems.core.dao.EmailDao;
import org.pahappa.systems.models.EmailTemplate;
import org.sers.webutils.server.core.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;

/**
*
* @author Eng.Ivan
*/

@Repository
public class EmailDaoImp extends BaseDAOImpl<EmailTemplate> implements EmailDao{

}
