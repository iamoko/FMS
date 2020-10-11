package org.pahappa.systems.views.transactions;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import org.pahappa.systems.core.services.WithDrawService;
import org.pahappa.systems.models.Withdraw;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean
@ViewScoped
@ViewPath(path = HyperLinks.HOMEPAGE)
public class WithdrawsView extends PaginatedTableView<Withdraw, WithdrawsView, WithdrawsView> {

	/**
	 * 
	 */

	private WithDrawService drawService;
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		this.drawService = ApplicationContextProvider.getApplicationContext().getBean(WithDrawService.class);
		super.setMaximumresultsPerpage(10);
		try {
			reloadFilterReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reloadFromDB(int arg0, int arg1, Map<String, Object> arg2) throws Exception {
		
		if (!SharedAppData.getLoggedInUser().hasAdministrativePrivileges()) {
			super.setTotalRecords(drawService.countWithdrawsByUser(SharedAppData.getLoggedInUser()));
			super.setDataModels(drawService.getTransactionsByUser(SharedAppData.getLoggedInUser()));
		}else {
			super.setTotalRecords(drawService.countWithdraws());
			super.setDataModels(drawService.getTransactions());
		}
		

	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}

}
