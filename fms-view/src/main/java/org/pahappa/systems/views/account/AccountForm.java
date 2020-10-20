package org.pahappa.systems.views.account;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

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
import org.sers.webutils.server.core.utils.DateUtils;

import java.util.Date;

import javax.faces.bean.*;

@ManagedBean(name = "accountForm")
@ViewScoped
@ViewPath(path = HyperLinks.ACCOUNTFORM)
public class AccountForm extends WebFormView<Deposit, AccountForm, AccountsView> {

    /**
     * Handles the failed operations to be shown to the user
     */
    private AccountService accountService;
    Account account;
    private int entAllowance, deposits;
    private DepositService depositService;
    private static final long serialVersionUID = 1L;

    private Date dateFro, dateTo;

    @Override
    public void beanInit() {
        resetModal();
        this.accountService = ApplicationContextProvider.getBean(AccountService.class);
        this.depositService = ApplicationContextProvider.getBean(DepositService.class);

    }

    @Override
    public void pageLoadInit() {
    }

    @Override
    public void persist() {

        try {
			/*CreditAccounts creditAccounts = new CreditAccounts(this.dateFro, this.dateTo);
			creditAccounts.start();
            MessageComposer.Compose("Action Successful", "All Accounts have Been Credited");*/

            CheckDeposited checkDeposit = new CheckDeposited(this.dateFro, this.dateTo);
            checkDeposit.start();
             /*super.model.setAmount(this.deposits);
            this.depositService.save(super.model);
            this.account = this.accountService.getAccountByUser(super.model.getUser());
            this.account.setOutstandingBalance((this.account.getOutstandingBalance() + (this.deposits)));
            this.accountService.save(this.account);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetModal() {
        super.resetModal();
        super.model = new Deposit();
        this.account = new Account();
    }

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateFrom() {
		return dateFro;
	}

	public void setDateFrom(Date dateFro) {
		this.dateFro = dateFro;
	}

	/**
     * @return the deposits
     */
    public int getDeposits() {
        return deposits;
    }

    /**
     * @param deposits the deposits to set
     */
    public void setDeposits(int deposits) {
        this.deposits = deposits;
    }

    /**
     * @return the entAllowance
     */
    public int getEntAllowance() {
        return entAllowance;
    }

    /**
     * @param entAllowance the entAllowance to set
     */
    public void setEntAllowance(int entAllowance) {
        this.entAllowance = entAllowance;
    }
}
