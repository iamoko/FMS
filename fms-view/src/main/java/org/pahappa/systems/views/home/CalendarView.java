package org.pahappa.systems.views.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.views.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean
@ViewScoped
@ViewPath(path = HyperLinks.CALENDARVIEW)
public class CalendarView  extends PaginatedTableView<Calendar, CalendarView, CalendarView> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private CalendarService calendarService;

    private Date startDate;
    private Date endDate;
    private String searchTerm;
    private SortField sortField;

    /**
     * Run this on bean initialization
     */
    @PostConstruct
    public void init() {
        this.calendarService = ApplicationContextProvider.getBean(CalendarService.class);
        super.setMaximumresultsPerpage(10);
        reloadFilterReset();
    }


    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) {
        this.setDataModels(this.calendarService.getCalendarDates(this.searchTerm, this.startDate, this.endDate, this.sortField, limit,offset));

    }


    @Override
    public void reloadFilterReset(){
        this.setTotalRecords(this.calendarService.countCalendarDates(this.searchTerm, this.startDate, this.endDate, this.sortField));
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

    public void delete(Calendar calendar){
        this.calendarService.deleteCalendar(calendar);
    }

}
