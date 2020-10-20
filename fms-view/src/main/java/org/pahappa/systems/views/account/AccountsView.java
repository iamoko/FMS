package org.pahappa.systems.views.account;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.googlecode.genericdao.search.Search;

import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.primefaces.PrimeFaces;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "accountsView")
@SessionScoped
@ViewPath(path = HyperLinks.ACCOUNTVIEW)
public class AccountsView extends PaginatedTableView<Account, AccountsView, AccountsView> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private AccountService accountService;
	private Search search;
	private String searchTerm;
	private List<SortField> sortFields;
	private SortField selectedSortField;
	private Date dateFrom, dateTo;

	@PostConstruct
	public void init() {
		this.accountService = ApplicationContextProvider.getBean(AccountService.class);
		this.sortFields = Arrays.asList(new SortField("First Name Asc", "user.firstName", false),
				new SortField("First Name Desc", "user.firstName", true),
				new SortField("Last Name Asc", "user.lastName", false),
				new SortField("Last Name Desc", "user.lastName", true),
				new SortField("Credit Date Asc", "dateCreated", false),
				new SortField("Credit Date Desc", "dateCreated", true));
		super.setMaximumresultsPerpage(10);
		reloadFilterReset();
	}

	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
		super.setDataModels(this.accountService.getAccounts(this.search, offset, limit));
	}

	@Override
	public void reloadFilterReset() {
		this.search = CustomSearchUtils.composeRequisitionFilterForAccounts(this.searchTerm, this.dateFrom, this.dateTo,
				this.selectedSortField);
		super.setTotalRecords(this.accountService.countAccounts(this.search));
		try {
			super.reloadFilterReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/*
	 * Change the number format
	 */
	public String currencyFormat(Account account) {
		NumberFormat myFormat = NumberFormat.getInstance();
		return myFormat.format(account.getOutstandingBalance());
	}

	/*
	 * Change the number format
	 */
	public String currencyFormatTwo(Account account) {
		NumberFormat myFormat = NumberFormat.getInstance();
		return myFormat.format(account.getEntitledAllowance());
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

	/**
	 * @return the sortFieldssortFields
	 */
	public List<SortField> getSortFields() {
		return sortFields;
	}

	/**
	 * @param sortFields the sortFields to set
	 */
	public void setSortFields(List<SortField> sortFields) {
		this.sortFields = sortFields;
	}

	/**
	 * @return the selectedSortField
	 */
	public SortField getSelectedSortField() {
		return selectedSortField;
	}

	/**
	 * @param selectedSortField the selectedSortField to set
	 */
	public void setSelectedSortField(SortField selectedSortField) {
		this.selectedSortField = selectedSortField;
	}

	public void delete(Account account) {
		try {
			this.accountService.delete(account);
			super.reloadFilterReset();
			MessageComposer.Compose("Action Successful", account.getUser().getFirstName() + " has been deleted");

		} catch (Exception e) {
			MessageComposer.Compose("Action Failed", e.getMessage());
		}

	}

}