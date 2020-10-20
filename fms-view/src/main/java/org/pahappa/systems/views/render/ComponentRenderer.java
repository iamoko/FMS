package org.pahappa.systems.views.render;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "pgwRenderer")
@SessionScoped
public class ComponentRenderer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean administrator = false;
	private boolean businessAdmin = false;
	private User loggedInUser;

	private boolean addUser = false;
	private boolean viewUsers = false;
	private boolean editUsers = false;

	private boolean updateAccount = false;
	private boolean viewAccounts = false;
	private boolean editAccounts = false;

	private boolean makeRequisition = false;
	private boolean viewRequisition = false;
	private boolean requisitionDetails = true;
	private boolean access = false;
	
	private boolean approveRequisition = false;
	private boolean declineRequisition = false;
	
	private boolean viewCalendar = false;
	
	private boolean viewWithdraws = false;
	private boolean viewDeposits = false;
	
	private boolean viewLogs = false;
	
	

	@PostConstruct
	public void init() {

		this.loggedInUser = SharedAppData.getLoggedInUser();
		this.administrator = this.loggedInUser.hasAdministrativePrivileges();
		
		/*
		 * Permissions on requisitions
		 */
		this.makeRequisition = this.loggedInUser.hasPermission(CustomPermissionConstants.MAKE_REQUISITION);
		this.viewRequisition = this.loggedInUser.hasPermission(CustomPermissionConstants.VIEW_REQUISITION);

		this.approveRequisition = this.loggedInUser.hasPermission(CustomPermissionConstants.APPROVE);
		this.declineRequisition = this.loggedInUser.hasPermission(CustomPermissionConstants.DECLINE);

		this.access = this.loggedInUser.hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS);
		/*
		 * Permissions on Users
		 */
		this.addUser = this.loggedInUser.hasPermission(CustomPermissionConstants.ADD_USERS);
		this.viewUsers = this.loggedInUser.hasPermission(CustomPermissionConstants.VIEW_USERS);
		this.editUsers = this.loggedInUser.hasPermission(CustomPermissionConstants.EDIT_USERS);
		/*
		 * Permissions on Accounts
		 */
		this.updateAccount = this.loggedInUser.hasPermission(CustomPermissionConstants.UPDATE_ACCOUNT);
		this.viewAccounts = this.loggedInUser.hasPermission(CustomPermissionConstants.VIEW_ACCOUNT);
		this.editAccounts = this.loggedInUser.hasPermission(CustomPermissionConstants.EDIT_ACCOUNT);
		/*
		 * Permissions on Calendar
		 */
		this.viewCalendar = this.loggedInUser.hasPermission(CustomPermissionConstants.HOLIDAYS);
		/*
		 * Permissions on Transactions
		 */
		this.viewDeposits = this.loggedInUser.hasPermission(CustomPermissionConstants.DEPOSITS);
		this.viewLogs = this.loggedInUser.hasPermission(CustomPermissionConstants.SYSTEM_LOGS);
		
	}

	public boolean isAccess() {
		return access;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}


	/**
	 * @return the approveRequisition
	 */
	public boolean isApproveRequisition() {
		return approveRequisition;
	}


	/**
	 * @param approveRequisition the approveRequisition to set
	 */
	public void setApproveRequisition(boolean approveRequisition) {
		this.approveRequisition = approveRequisition;
	}


	/**
	 * @return the declineRequisition
	 */
	public boolean isDeclineRequisition() {
		return declineRequisition;
	}


	/**
	 * @param declineRequisition the declineRequisition to set
	 */
	public void setDeclineRequisition(boolean declineRequisition) {
		this.declineRequisition = declineRequisition;
	}


	/**
	 * @return the viewCalendar
	 */
	public boolean isViewCalendar() {
		return viewCalendar;
	}


	/**
	 * @param viewCalendar the viewCalendar to set
	 */
	public void setViewCalendar(boolean viewCalendar) {
		this.viewCalendar = viewCalendar;
	}


	/**
	 * @return the viewWithdraws
	 */
	public boolean isViewWithdraws() {
		return viewWithdraws;
	}


	/**
	 * @param viewWithdraws the viewWithdraws to set
	 */
	public void setViewWithdraws(boolean viewWithdraws) {
		this.viewWithdraws = viewWithdraws;
	}


	/**
	 * @return the viewDeposits
	 */
	public boolean isViewDeposits() {
		return viewDeposits;
	}


	/**
	 * @param viewDeposits the viewDeposits to set
	 */
	public void setViewDeposits(boolean viewDeposits) {
		this.viewDeposits = viewDeposits;
	}


	/**
	 * @return the viewLogs
	 */
	public boolean isViewLogs() {
		return viewLogs;
	}


	/**
	 * @param viewLogs the viewLogs to set
	 */
	public void setViewLogs(boolean viewLogs) {
		this.viewLogs = viewLogs;
	}



	/**
	 * @return the editUsers
	 */
	public boolean isEditUsers() {
		return editUsers;
	}

	/**
	 * @param editUsers the editUsers to set
	 */
	public void setEditUsers(boolean editUsers) {
		this.editUsers = editUsers;
	}


	/**
	 * @return the editAccounts
	 */
	public boolean isEditAccounts() {
		return editAccounts;
	}

	/**
	 * @param editAccounts the editAccounts to set
	 */
	public void setEditAccounts(boolean editAccounts) {
		this.editAccounts = editAccounts;
	}

	/**
	 * @return the addUser
	 */
	public boolean isAddUser() {
		return addUser;
	}

	/**
	 * @param addUser the addUser to set
	 */
	public void setAddUser(boolean addUser) {
		this.addUser = addUser;
	}

	/**
	 * @return the viewUsers
	 */
	public boolean isViewUsers() {
		return viewUsers;
	}

	/**
	 * @param viewUsers the viewUsers to set
	 */
	public void setViewUsers(boolean viewUsers) {
		this.viewUsers = viewUsers;
	}

	/**
	 * @return the updateAccount
	 */
	public boolean isUpdateAccount() {
		return updateAccount;
	}

	/**
	 * @param updateAccount the updateAccount to set
	 */
	public void setUpdateAccount(boolean updateAccount) {
		this.updateAccount = updateAccount;
	}

	/**
	 * @return the requisitionDetails
	 */
	public boolean isRequisitionDetails() {
		return requisitionDetails;
	}

	/**
	 * @param requisitionDetails the requisitionDetails to set
	 */
	public void setRequisitionDetails(boolean requisitionDetails) {
		this.requisitionDetails = requisitionDetails;
	}

	/**
	 * @return the viewAccounts
	 */
	public boolean isViewAccounts() {
		return viewAccounts;
	}

	/**
	 * @param viewAccounts the viewAccounts to set
	 */
	public void setViewAccounts(boolean viewAccounts) {
		this.viewAccounts = viewAccounts;
	}

	/**
	 * @return the makeRequisition
	 */
	public boolean isMakeRequisition() {
		return makeRequisition;
	}

	/**
	 * @param makeRequisition the makeRequisition to set
	 */
	public void setMakeRequisition(boolean makeRequisition) {
		this.makeRequisition = makeRequisition;
	}

	/**
	 * @return the viewRequisition
	 */
	public boolean isViewRequisition() {
		return viewRequisition;
	}

	/**
	 * @param viewRequisition the viewRequisition to set
	 */
	public void setViewRequisition(boolean viewRequisition) {
		this.viewRequisition = viewRequisition;
	}

	/**
	 * @return the loggedInUser
	 */
	public User getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * @param loggedInUser the loggedInUser to set
	 */
	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	/**
	 * @return the administrator
	 */
	public boolean isAdministrator() {
		return administrator;
	}

	/**
	 * @param administrator the administrator to set
	 */
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	/**
	 * @return the businessAdmin
	 */
	public boolean isBusinessAdmin() {
		return businessAdmin;
	}

	/**
	 * @param businessAdmin the businessAdmin to set
	 */
	public void setBusinessAdmin(boolean businessAdmin) {
		this.businessAdmin = businessAdmin;
	}
}