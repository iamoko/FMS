package org.pahappa.systems.views.home;

import java.io.Serializable;
import java.text.NumberFormat;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.RequisitionService;
import org.pahappa.systems.models.RequisitionStatus;
import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.controllers.WebAppExceptionHandler;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

import com.googlecode.genericdao.search.Search;

@ManagedBean
@RequestScoped
@ViewPath(path = HyperLinks.HOMEPAGE)
public class HomePage extends WebAppExceptionHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private String loggedinUser;
	private String logoutUrl = "ServiceLogout";
	private int users;
	private String acknowledged;
	@SuppressWarnings("unused")
	private String viewPath;
	User user;
	UserService userService;

	private int requisitions, pending, rejected, approved;

	String outstandingBalance;
	RequisitionService requisitionService;
	AccountService accountService;
	NumberFormat myFormat = NumberFormat.getInstance();
	private String email;

	private Boolean ackNeeded = false;

	@PostConstruct
	public void init() {
		if (SharedAppData.getLoggedInUser() != null) {
			this.loggedinUser = SharedAppData.getLoggedInUser().getFullName();
			this.email = SharedAppData.getLoggedInUser().getEmailAddress();
			this.user = SharedAppData.getLoggedInUser();
			this.requisitionService = ApplicationContextProvider.getBean(RequisitionService.class);
			this.accountService = ApplicationContextProvider.getBean(AccountService.class);
			this.userService = ApplicationContextProvider.getBean(UserService.class);

		}

		this.pending = this.requisitionService.countRequisitionsByStatus(RequisitionStatus.ACKNOWLEDGED);
		this.rejected = this.requisitionService.countRequisitionsByStatus(RequisitionStatus.DECLINED);
		this.approved = this.requisitionService.countRequisitionsByStatus(RequisitionStatus.APPROVED);

		Search search = new Search();
		try {
			if (this.user.hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
				this.requisitions = this.requisitionService.countRequisitions(search);
				this.users = this.userService.countUsers(search) - 1;
				this.outstandingBalance = this.myFormat
						.format(this.accountService.getAccountByUser(this.user).getOutstandingBalance());

			} else {
				this.outstandingBalance = this.myFormat
						.format(this.accountService.getAccountByUser(this.user).getOutstandingBalance());
				search.addFilterEqual("requisitionStatus", RequisitionStatus.APPROVED);

				this.acknowledged = this.myFormat.format(
						this.requisitionService.countRequisitionsByUser(search, SharedAppData.getLoggedInUser()));
				if (this.acknowledged.length() > 0) {
					this.ackNeeded = true;
				}
			}

		} catch (Exception e) {
			this.requisitions = 0;
		}

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the acknowledged
	 */
	public String getAcknowledged() {
		return acknowledged;
	}

	/**
	 * @param acknowledged the acknowledged to set
	 */
	public void setAcknowledged(String acknowledged) {
		this.acknowledged = acknowledged;
	}

	/**
	 * @return the ackNeeded
	 */
	public Boolean getAckNeeded() {
		return ackNeeded;
	}

	/**
	 * @param ackNeeded the ackNeeded to set
	 */
	public void setAckNeeded(Boolean ackNeeded) {
		this.ackNeeded = ackNeeded;
	}

	public void handleKeyEvent() {
		outstandingBalance = outstandingBalance.toUpperCase();
	}

	/**
	 * @return the users
	 */
	public int getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(int users) {
		this.users = users;
	}

	/**
	 * @return the outstandingBalance
	 */
	public String getOutstandingBalance() {
		return outstandingBalance;
	}

	/**
	 * @param outstandingBalance the outstandingBalance to set
	 */
	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	/**
	 * @return the requisitionService
	 */
	public RequisitionService getRequisitionService() {
		return requisitionService;
	}

	/**
	 * @param requisitionService the requisitionService to set
	 */
	public void setRequisitionService(RequisitionService requisitionService) {
		this.requisitionService = requisitionService;
	}

	/**
	 * @return the pending
	 */
	public int getPending() {
		return pending;
	}

	/**
	 * @param pending the pending to set
	 */
	public void setPending(int pending) {
		this.pending = pending;
	}

	/**
	 * @return the rejected
	 */
	public int getRejected() {
		return rejected;
	}

	/**
	 * @param rejected the rejected to set
	 */
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	/**
	 * @return the approved
	 */
	public int getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(int approved) {
		this.approved = approved;
	}

	/**
	 * @return the loggedinUser
	 */
	public String getLoggedinUser() {
		return loggedinUser;
	}

	/**
	 * @param loggedinUser the loggedinUser to set
	 */
	public void setLoggedinUser(String loggedinUser) {
		this.loggedinUser = loggedinUser;
	}

	/**
	 * @return the logoutUrl
	 */
	public String getLogoutUrl() {
		// super.invalidateSession();
		return logoutUrl;
	}

	/**
	 * @return the requisitions
	 */
	public int getRequisitions() {
		return requisitions;
	}

	/**
	 * @param requisitions the requisitions to set
	 */
	public void setRequisitions(int requisitions) {
		this.requisitions = requisitions;
	}

	/**
	 * @param logoutUrl the logoutUrl to set
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * @return the viewPath
	 */
	public String getViewPath() {
		return HomePage.class.getAnnotation(ViewPath.class).path();
	}

	/**
	 * @param viewPath the viewPath to set
	 */
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}
}
