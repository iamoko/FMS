package org.pahappa.systems.views.account;

import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

@ManagedBean(name = "userCalendarForm")
@SessionScoped
@ViewPath(path = HyperLinks.USERDAYS)
public class UserCalendarForm extends WebFormView<Account, UserCalendarForm, UserCalendarForm> {

    private AccountService accountService;
    private static final long serialVersionUID = 1L;


    @Override
    public void beanInit() {
        resetModal();
        this.accountService = ApplicationContextProvider.getBean(AccountService.class);
    }

    @Override
    public void pageLoadInit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void persist() {

    }

    public void resetModal() {
        super.resetModal();
        super.model = new Account();
    }

}
