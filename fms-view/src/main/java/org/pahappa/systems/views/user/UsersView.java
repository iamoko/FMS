package org.pahappa.systems.views.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.EmployeeSearchUtils;
import org.pahappa.systems.views.StoreLogs;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.security.User;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.server.core.dao.UserDao;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import com.googlecode.genericdao.search.Search;

@ManagedBean
@SessionScoped
@ViewPath(path = HyperLinks.USERSVIEW)
public class UsersView extends PaginatedTableView<User, UsersView, UsersView> {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private String searchTerm;
	private Search search;
	private List<SearchField> searchFields, selectedSearchFields;
	private List<Gender> listOfGenders;
	private Gender selectedGender;
	private Date bornFrom, bornTo;

	@PostConstruct
	public void init() {
		this.userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
		this.listOfGenders = Arrays.asList(Gender.values());
		this.searchFields = Arrays.asList(new SearchField[] { new SearchField("First Name", "firstName"),
				new SearchField("Last Name", "lastName") });
		this.selectedSearchFields = new ArrayList<SearchField>(this.searchFields);
		super.setMaximumresultsPerpage(10);
		reloadFilterReset();
	}

	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {
		this.search.addFilterNotEqual("username", "administrator");
		super.setDataModels(userService.getUsers(this.search, offset, limit));
	}

	@Override
	public void reloadFilterReset() {
		this.search = EmployeeSearchUtils.composeEmployeeSearch(this.selectedSearchFields, this.searchTerm,
				this.selectedGender, this.bornFrom, this.bornTo);
		this.search.addFilterNotEqual("username", "administrator");
		super.setTotalRecords(userService.countUsers(this.search));
		try {
			super.reloadFilterReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @return the searchFields
	 */
	public List<SearchField> getSearchFields() {
		return searchFields;
	}

	/**
	 * @return the listOfGenders
	 */
	public List<Gender> getListOfGenders() {
		return listOfGenders;
	}

	/**
	 * @return the selectedGender
	 */
	public Gender getSelectedGender() {
		return selectedGender;
	}

	/**
	 * @return the bornFrom
	 */
	public Date getBornFrom() {
		return bornFrom;
	}

	/**
	 * @return the bornTo
	 */
	public Date getBornTo() {
		return bornTo;
	}

	/**
	 * @param searchFields the searchFields to set
	 */
	public void setSearchFields(List<SearchField> searchFields) {
		this.searchFields = searchFields;
	}

	/**
	 * @param listOfGenders the listOfGenders to set
	 */
	public void setListOfGenders(List<Gender> listOfGenders) {
		this.listOfGenders = listOfGenders;
	}

	/**
	 * @param selectedGender the selectedGender to set
	 */
	public void setSelectedGender(Gender selectedGender) {
		this.selectedGender = selectedGender;
	}

	/**
	 * @param bornFrom the bornFrom to set
	 */
	public void setBornFrom(Date bornFrom) {
		this.bornFrom = bornFrom;
	}

	/**
	 * @param bornTo the bornTo to set
	 */
	public void setBornTo(Date bornTo) {
		this.bornTo = bornTo;
	}

	/**
	 * @return the searchTerm
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * @param searchTerm the searchTerm to set
	 */
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		return null;
	}

	@Override
	public String getFileName() {
		return null;
	}
}