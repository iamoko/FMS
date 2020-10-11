package org.pahappa.systems.models.permissions;

public class CustomPermissionConstants {

	@CustomPermAnnotation(name = "Access Employee Withdraws", description = "Allows its holder to Access Withdraws")
	public static final String ACCESS_WITHDRAW = "Access Employee Withdraws";

	@CustomPermAnnotation(name = "Access Employee Records", description = "Allows its holder to Access Withdraws")
	public static final String ACCESS_REQUISITIONS = "Access Employee Withdraws";

	@CustomPermAnnotation(name = "Approve Resuisition", description = "Allows its holder to view account details")
	public static final String APPROVE = "Edit Account Details";

	@CustomPermAnnotation(name = "Decline Resuisition", description = "Allows its holder to view account details")
	public static final String DECLINE = "Edit Account Details";

	@CustomPermAnnotation(name = "Add Requisition", description = "Allows its holder to add a requisition to the database")
	public static final String MAKE_REQUISITION = "Make Requisition";

	@CustomPermAnnotation(name = "View Requisitions", description = "Allows its holder to view requisitions")
	public static final String VIEW_REQUISITION = "View Requisitions";
	
	@CustomPermAnnotation(name = "Add User", description = "Allows its holder to add a user to the database")
	public static final String ADD_USERS = "Add User";

	@CustomPermAnnotation(name = "View Users", description = "Allows its holder to view Users")
	public static final String VIEW_USERS = "View Users";

	@CustomPermAnnotation(name = "Edit Users", description = "Allows its holder to edit Users")
	public static final String EDIT_USERS = "View Edit";

	@CustomPermAnnotation(name = "Update Account Details", description = "Allows its holder to view account details")
	public static final String UPDATE_ACCOUNT = "Update Account Details";
	
	@CustomPermAnnotation(name = "View Account Details", description = "Allows its holder to view account details")
	public static final String VIEW_ACCOUNT = "View Account Details";
	
	@CustomPermAnnotation(name = "Edit Account Details", description = "Allows its holder to view account details")
	public static final String EDIT_ACCOUNT = "Edit Account Details";
	
	
	@CustomPermAnnotation(name = "Set Calendar Holidays", description = "Allows its holder to View Events list")
	public static final String HOLIDAYS = "Set Calendar Holidays";

	@CustomPermAnnotation(name = "View Withdraws", description = "Allows its holder to Access Withdraws")
	public static final String VIEW_WITHDRAW = "View Employee Withdraws";

	
	@CustomPermAnnotation(name = "View Employee Deposits", description = "Allows its holder to Access Deposits")
	public static final String DEPOSITS = "View Employee Deposits";
	
	@CustomPermAnnotation(name = "View System Logs", description = "Allows its holder to View System Logs")
	public static final String SYSTEM_LOGS = "View System Logs";
	
	
	
	
}
