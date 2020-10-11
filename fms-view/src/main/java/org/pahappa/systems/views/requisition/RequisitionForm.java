package org.pahappa.systems.views.requisition;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.RequisitionService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.BeansException;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;

@ManagedBean(name = "requisitionForm")
@ViewScoped
@ViewPath(path = HyperLinks.REQUISITIONFORM)
public class RequisitionForm extends WebFormView<Requisition, RequisitionForm, RequisitionsView> {

    /**
     * Handles the failed operations to be shown to the user
     */
    private String failed;
    private static final long serialVersionUID = 1L;
    private String text;
    Account account;
    private Date startDate, endDate;
    AccountService accountService;
    RequisitionService requisitionService;
    User loggedInUser;
    UserService userService;
    private Boolean showInfo = false;
    private int amountRequested, days;

    @Override
    public void beanInit() {
        resetModal();
        this.requisitionService = ApplicationContextProvider.getBean(RequisitionService.class);
        this.accountService = ApplicationContextProvider.getBean(AccountService.class);
        this.userService = ApplicationContextProvider.getBean(UserService.class);
        this.loggedInUser = SharedAppData.getLoggedInUser();
        this.showInfo = false;
    }

    public Boolean getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(Boolean showInfo) {
        this.showInfo = showInfo;
    }

    @Override
    public void pageLoadInit() {
    }


    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the amountRequested
     */
    public int getAmountRequested() {
        return amountRequested;
    }

    /**
     * @param amountRequested the amountRequested to set
     */
    public void setAmountRequested(int amountRequested) {
        this.amountRequested = amountRequested;
    }

    @Override
    public void persist() {

        try {
            StoreLogs.Log("Requisition " + super.model.getRequisitionNumber() + " for " + this.days + " day(s) for an amount of " + this.amountRequested + "/=");
            super.model.setDaysRequested((int) this.days);
            super.model.setAmountRequested(this.amountRequested);
            this.requisitionService.save(super.model);
            MessageComposer.Compose("Action Successful", super.model.getRequisitionNumber() + "Requisition Created");
        } catch (Exception e) {
            e.printStackTrace();
            MessageComposer.Failed("Action failed", e.getMessage());
        }
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    public void resetModal() {
        super.resetModal();
        super.model = new Requisition();
        account = new Account();
    }

    public void deleteRequisition(Requisition requisition) throws BeansException, OperationFailedException {
        ApplicationContextProvider.getApplicationContext().getBean(RequisitionService.class)
                .deleteRequisition(requisition);
    }

    /**
     * @return the failed
     */
    public String getFailed() {
        return failed;
    }

    /**
     * @param failed the failed to set
     */
    public void setFailed(String failed) {
        this.failed = failed;
    }

    /**
     * @return the days
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(int days) {
        this.days = days;
    }

    public void computeRequisitionByDateRange() {
        this.account = accountService.getAccountByUser(this.loggedInUser);
        this.days = this.requisitionService.getDifferenceDays(super.model.getStartDate(), super.model.getEndDate());
        this.amountRequested = (int) (this.days * this.account.getEntitledAllowance());
        this.showInfo = true;
    }
}
