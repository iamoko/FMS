package org.pahappa.systems.views.account;

import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.primefaces.PrimeFaces;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

@ManagedBean(name = "accountUpdateForm")
@SessionScoped
@ViewPath(path = HyperLinks.ACCOUNTUPDATEFORM)
public class AccountUpdateForm extends WebFormView<Account, AccountUpdateForm, AccountsView> {

    /**
     * Handles the failed operations to be shown to the user
     */
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

        try {

            MessageComposer.Compose("Action Successful", "User Entitled Allowance Updated");
            StoreLogs.Log("Updated " + super.model.getUser().getFullName() + "'s account");
            this.accountService.save(super.model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetModal() {
        super.resetModal();
        super.model = new Account();
    }
}
