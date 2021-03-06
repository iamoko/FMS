package org.pahappa.systems.views.requisition;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.pahappa.systems.core.services.*;
import org.pahappa.systems.core.services.impl.MailService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.faces.bean.*;

@ManagedBean(name = "requisitionDetails")
@SessionScoped
@ViewPath(path = HyperLinks.REQUISITIONDETAILS)
public class RequisitionDetails extends WebFormView<Requisition, RequisitionDetails, RequisitionDetails> {

    private static final long serialVersionUID = 1L;
    /*
     * Handle Days Calculations
     */
    Account account;
    TemplateService templateService;
    AccountService accountService;
    RequisitionService requisitionService;
    EmailTemplateService emailTemplateService;
    User loggedInUser;
    UserService userService;
    private String comment;
    String msg, heading;

    @Override
    public void beanInit() {
        resetModal();
        this.requisitionService = ApplicationContextProvider.getBean(RequisitionService.class);
        this.accountService = ApplicationContextProvider.getBean(AccountService.class);
        this.userService = ApplicationContextProvider.getBean(UserService.class);
        this.templateService = ApplicationContextProvider.getBean(TemplateService.class);
        this.emailTemplateService = ApplicationContextProvider.getBean(EmailTemplateService.class);
        this.loggedInUser = SharedAppData.getLoggedInUser();

    }

    @Override
    public void pageLoadInit() {

    }

    public Boolean checkApproval(Requisition requisition) {
        return (requisition.getRequisitionStatus() == RequisitionStatus.APPROVED) && (requisition.getUser().equals(SharedAppData.getLoggedInUser()));
    }

    public String getBalance(Requisition requisition) {
        NumberFormat myFormat = NumberFormat.getInstance();
        return myFormat.format((int) this.accountService.getAccountByUser(requisition.getUser()).getOutstandingBalance());
    }

    public Boolean checkStatus(Requisition requisition) {
        this.requisitionService.setViewedRequisition(requisition);
        StoreLogs.Log("Requisition " + requisition.getRequisitionNumber() + " has been Viewed by " + SharedAppData.getLoggedInUser().getFullName());
        return requisition.getRequisitionStatus() == RequisitionStatus.PENDING;
    }

    public String DateFormatedStart(Date requisition) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-LLL-yyyy");
        return DateFor.format(requisition);
    }

    @Override
    public void persist() {
        try {
            this.requisitionService.save(super.model);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getDeclineComment(Requisition requisition) {
        if (requisition.getRequisitionStatus() == RequisitionStatus.DECLINED)
            return "Reason : " + requisition.getComment();
        return "";
    }

    public Boolean checkDec(Requisition requisition) {
        return requisition.getRequisitionStatus() == RequisitionStatus.DECLINED;
    }


    public void resetModal() {
        super.resetModal();
        super.model = new Requisition();
        account = new Account();
    }

    List<String> customVariableList = new ArrayList<String>();

    public String messageGenerator(String message, Requisition requisition) {
        /**
         * Append constants to the Array
         */
        this.customVariableList.add("\\{name\\}");
        this.customVariableList.add("\\{amount\\}");
        this.customVariableList.add("\\{requisition\\}");

        for (String customVariable : this.customVariableList) {
            switch (customVariable) {
                case "\\{name\\}":
                    message = message.replaceAll(customVariable, requisition.getUser().getFullName());
                    break;
                case "\\{amount\\}":
                    message = message.replaceAll(customVariable, "" + requisition.getAmountRequested());
                    break;
                case "\\{requisition\\}":
                    message = message.replaceAll(customVariable, "" + requisition.getRequisitionNumber());
                    break;
            }
        }

        System.out.println("--------FINAL MESSAGE" + message);
        return message;
    }

    public void approveRequisition(Requisition requisition) {
		if (this.accountService.getAccountByUser(requisition.getUser()).getOutstandingBalance() >= requisition
				.getAmountRequested()) {
			
			try {
				this.requisitionService.changeRequisitionStatus(this.account, requisition, RequisitionStatus.APPROVED,
						null);

                this.heading = "Requisition Approved";
                this.msg = messageGenerator(this.emailTemplateService.getTemplateByStatus(RequisitionStatus.APPROVED).getTemplate(), requisition);

                MailService mailService = new MailService(requisition.getUser(), this.heading, this.msg);
                mailService.start();

				StoreLogs.Log("The Requisition "+requisition.getRequisitionNumber()+" has been Approved");
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect(((HttpServletRequest) externalContext.getRequest()).getRequestURI());
                MessageComposer.Compose("Action Successful", requisition.getUser()+"'s Requisition "+requisition.getRequisitionNumber()+" for "+requisition.getAmountRequested()+" has been Approved.");

            } catch (Exception e) {
				e.printStackTrace();
				MessageComposer.Compose("Action Failed", e.getMessage());
			}
		} else {
			MessageComposer.Failed("Action Failed", "Amount requested exceeds the employee's Outstanding Balance");
		}

    }

    public void declineRequisition(Requisition requisition) {

        try {

            this.requisitionService.changeRequisitionStatus(this.account, requisition, RequisitionStatus.DECLINED,
                    this.comment);
            this.heading = "Requisition Declined";
            this.msg = messageGenerator(this.emailTemplateService.getTemplateByStatus(RequisitionStatus.DECLINED).getTemplate(), requisition);

            MailService mailService = new MailService(requisition.getUser(), this.heading, this.msg);
            mailService.start();

            StoreLogs.Log("The Requisition " + requisition.getRequisitionNumber() + " is Declined");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(((HttpServletRequest) externalContext.getRequest()).getRequestURI());
            MessageComposer.Compose("Action Successful", requisition.getUser() + "'s Requisition " + requisition.getRequisitionNumber() + " for " + requisition.getAmountRequested() + " has been Declined.");

        } catch (Exception e) {
            MessageComposer.Failed("Action failed", e.getMessage());
        }
    }

    public Boolean checkUser(Requisition requisition) {
        return !requisition.getUser().equals(SharedAppData.getLoggedInUser());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void acknowledgeRequisition(Requisition requisition) {

        try {
            this.heading = "Requisition Acknowleged";
            this.msg = messageGenerator(this.emailTemplateService.getTemplateByStatus(RequisitionStatus.ACKNOWLEDGED).getTemplate(), requisition);

            MailService mailService = new MailService(requisition.getChangedBy(), this.heading, this.msg);
            mailService.start();

            this.requisitionService.changeRequisitionStatus(this.account, requisition, RequisitionStatus.ACKNOWLEDGED,
                    null);

            MessageComposer.Compose("Action Successful", "Requisition has been Acknowledged\nContact " + requisition.getChangedBy().getFullName() + " on " + requisition.getChangedBy().getPhoneNumber() + "For further instructions");
            StoreLogs.Log("The Requisition " + requisition.getRequisitionNumber() + " has been Acknowledged");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(((HttpServletRequest) externalContext.getRequest()).getRequestURI());

        } catch (Exception e) {
            e.printStackTrace();
            MessageComposer.Failed("Action failed", e.getMessage());
        }
    }

}
