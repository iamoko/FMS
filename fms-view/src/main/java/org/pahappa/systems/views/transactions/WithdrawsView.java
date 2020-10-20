package org.pahappa.systems.views.transactions;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.core.services.WithDrawService;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.models.Withdraw;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;



@ManagedBean
@ViewScoped
@ViewPath(path = HyperLinks.HOMEPAGE)
public class WithdrawsView extends PaginatedTableView<Withdraw, WithdrawsView, WithdrawsView>{

	/**
	 *
	 */
	private List<Withdraw> filteredTemplate;
	private int depositCount;
	private WithDrawService withDrawService;
	private static final long serialVersionUID = 1L;
	private Date startDate, endDate;
	private String searchTerm;
	private SortField sortField;

	private List<SortField> sortFields;
	private SortField selectedSortField;

	@PostConstruct
	public void init() {
		this.withDrawService = ApplicationContextProvider.getApplicationContext().getBean(WithDrawService.class);
		this.sortFields = Arrays.asList(new SortField("First Name Asc", "user.firstName", false),
				new SortField("First Name Desc", "user.firstName", true),
				new SortField("Last Name Asc", "user.lastName", false),
				new SortField("Last Name Desc", "user.lastName", true),
				new SortField("Withdraw Date Asc", "dateCreated", false),
				new SortField("Withdraw Date Desc", "dateCreated", true));
		super.setMaximumresultsPerpage(10);
		reloadFilterReset();
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String DateFormated(Date date ) {
		SimpleDateFormat DateFor = new SimpleDateFormat("dd-LLL-yyyy");
		return DateFor.format(date);
	}

	public String currencyFormat(int amount) {
		NumberFormat myFormat = NumberFormat.getInstance();
		return myFormat.format(amount);
	}

	@Override
	public void reloadFilterReset(){
		this.setTotalRecords(this.withDrawService.countSearchedWithdraws(this.searchTerm, this.startDate, this.endDate, this.sortField));
		try {
			super.reloadFilterReset();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
		super.setDataModels(withDrawService.getUserWithdraws(this.searchTerm, this.startDate, this.endDate, this.selectedSortField, limit,offset));
	}

	public SortField getSelectedSortField() {
		return selectedSortField;
	}

	public void setSelectedSortField(SortField selectedSortField) {
		this.selectedSortField = selectedSortField;
	}

	public List<SortField> getSortFields() {
		return sortFields;
	}

	public void setSortFields(List<SortField> sortFields) {
		this.sortFields = sortFields;
	}

	/**
	 * @return the depositCount
	 */
	public int getDepositCount() {
		return depositCount;
	}

	/**
	 * @param depositCount the depositCount to set
	 */
	public void setDepositCount(int depositCount) {
		this.depositCount = depositCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public SortField getSortField() {
		return sortField;
	}

	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}


}
