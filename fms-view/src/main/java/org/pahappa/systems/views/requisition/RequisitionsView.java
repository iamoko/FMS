/**
 *
 */
package org.pahappa.systems.views.requisition;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.RequisitionSearchUtils;
import org.pahappa.systems.core.services.RequisitionService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.pahappa.systems.views.security.HyperLinks;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

import com.googlecode.genericdao.search.Search;

/**
 * @author Eng.Ivan
 *
 */
@ManagedBean(name = "requisitionsView")
@ViewScoped
@ViewPath(path = HyperLinks.REQUISITIONVIEW)
public class RequisitionsView extends PaginatedTableView<Requisition, RequisitionsView, RequisitionsView> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    RequisitionService requisitionService;
    private Search search;
    private String searchTerm;
    private List<SortField> sortFields;
    private SortField selectedSortField;
    private List<SearchField> searchFields, selectedSearchFields;
    private List<RequisitionStatus> statusFields = new ArrayList<RequisitionStatus>();

    private Boolean stillpending = true, approvedRequisition = false;
    private Date dateFrom, dateTo;

    Account account;
    AccountService accountService;
    User user;
    Double outstandingBalance;

    private Boolean statusOfRequisition = false;

    @PostConstruct
    public void init() {
        this.requisitionService = ApplicationContextProvider.getBean(RequisitionService.class);
        this.accountService = ApplicationContextProvider.getBean(AccountService.class);
        this.sortFields = Arrays.asList(
                new SortField("Reqsuitision Number Desc", "requisitionNumber", true),
                new SortField("Reqsuitision Number ASC", "requisitionNumber", false),
                new SortField("Date Created Asc", "dateCreated", false),
                new SortField("Date Created Desc", "dateCreated", true),

                new SortField("Amount Requested Asc", "amountRequested", false),
                new SortField("Amount Requested  Desc", "amountRequested", true));

        super.setMaximumresultsPerpage(10);
        this.searchFields = Arrays.asList(
                new SearchField("Full Name", "fullName"),
                new SearchField("First Name", "firstName"),
                new SearchField("Last Name", "lastName")
        );

        this.selectedSearchFields = new ArrayList<SearchField>(this.searchFields);
        this.searchTerm = "";
        reloadFilterReset();

        this.user = SharedAppData.getLoggedInUser();

        Search search = new Search();
        search.addFilterEqual("requisitionStatus", RequisitionStatus.PENDING);
        if ((this.requisitionService.countReq(search) > 0)) {
            this.stillpending = false;
        }
        search = new Search();
        search.addFilterEqual("requisitionStatus", RequisitionStatus.APPROVED);
        if (this.requisitionService.countReq(search) > 0) {
            this.stillpending = false;
        }

//		this.requisitionService.getRequisitionByStatus(RequisitionStatus.APPROVED);
        this.approvedRequisition = true;

    }

    public String seen(Boolean bool) {
        if(!bool){
            return "Unseen";
        }
        return "Seen";
    }
    public String DateFormatedStart(Requisition requisition) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-LLL-yyyy");
        return DateFor.format(requisition.getStartDate());
    }

    public String DateFormatedEnd(Requisition requisition) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-LLL-yyyy");
        return DateFor.format(requisition.getEndDate());
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public List<RequisitionStatus> getStatusFields() {
        return statusFields;
    }

    public void setStatusFields(List<RequisitionStatus> statusFields) {
        this.statusFields = statusFields;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the approvedRequisition
     */
    public Boolean getApprovedRequisition() {
        return approvedRequisition;
    }

    /**
     * @param approvedRequisition the approvedRequisition to set
     */
    public void setApprovedRequisition(Boolean approvedRequisition) {
        this.approvedRequisition = approvedRequisition;
    }

    /**
     * @return the stillpending
     */
    public Boolean getStillpending() {
        return stillpending;
    }

    /**
     * @param stillpending the stillpending to set
     */
    public void setStillpending(Boolean stillpending) {
        this.stillpending = stillpending;
    }

    /*
     * Change the number format
     */
    public String currencyFormat(Requisition requisition) {
        NumberFormat myFormat = NumberFormat.getInstance();
        return myFormat.format(requisition.getAmountRequested());
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

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
        super.setDataModels(requisitionService.getRequisitions(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = CustomSearchUtils.composeRequisitionFilterForRequisitions(this.searchTerm, this.dateFrom, this.dateTo,
                this.selectedSortField);
        super.setTotalRecords(this.requisitionService.countRequisitions(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(Requisition requisition) {
        try {
            this.requisitionService.deleteRequisition(requisition);
            super.reloadFilterReset();
            MessageComposer.Compose("Action Successful",requisition.getRequisitionNumber() + " has been deleted");

        } catch (Exception e) {
            MessageComposer.Compose("Action failed", e.getMessage());
        }

    }

    /**
     * @return the searchFields
     */
    public List<SearchField> getSearchFields() {
        return searchFields;
    }

    /**
     * @param searchFields the searchFields to set
     */
    public void setSearchFields(List<SearchField> searchFields) {
        this.searchFields = searchFields;
    }

    /**
     * @return the selectedSearchFields
     */
    public List<SearchField> getSelectedSearchFields() {
        return selectedSearchFields;
    }

    /**
     * @param selectedSearchFields the selectedSearchFields to set
     */
    public void setSelectedSearchFields(List<SearchField> selectedSearchFields) {
        this.selectedSearchFields = selectedSearchFields;
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
     * @return the sortFields
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

    /**
     * @return the statusOfRequisition
     */
    public Boolean getStatusOfRequisition() {
        return statusOfRequisition;
    }

    /**
     * @param statusOfRequisition the statusOfRequisition to set
     */
    public void setStatusOfRequisition(Boolean statusOfRequisition) {
        this.statusOfRequisition = statusOfRequisition;
    }
}
