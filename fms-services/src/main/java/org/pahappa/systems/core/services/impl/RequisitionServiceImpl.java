package org.pahappa.systems.core.services.impl;

import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.core.API.Withdrawing;
import org.pahappa.systems.core.dao.WithdrawDao;
import org.pahappa.systems.core.services.*;
import org.pahappa.systems.models.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.pahappa.systems.core.dao.AccountDao;
import org.pahappa.systems.core.dao.RequistionDao;
import org.pahappa.systems.models.permissions.CustomPermissionConstants;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pahappa.systems.core.services.impl.API.APIWithdraw;

/**
 * @author Eng.Ivan
 */
@Service
@Transactional
public class RequisitionServiceImpl implements RequisitionService {


    private String message;

    /*
     * Instantiation of the Data Access Object
     */
    @Autowired
    RequistionDao requisitiondao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    WithdrawDao withdrawDao;

    Search search;

    public String generateReqNo() {
        Search search = new Search();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        Date date = new Date();

        String build = String.valueOf(SharedAppData.getLoggedInUser().getFirstName().toUpperCase().charAt(0)) +
                SharedAppData.getLoggedInUser().getLastName().toUpperCase().charAt(0);
        return "R"+ build + "/" + dateFormat.format(date) + "/" + (requisitiondao.count(search) + 1);
    }

    @Override
    public void save(Requisition requisition) {
        requisition.setRequisitionStatus(RequisitionStatus.PENDING);
        requisition.setRequisitionNumber(generateReqNo());
        requisition.setUser(SharedAppData.getLoggedInUser());
        requisition.setRecordStatus(RecordStatus.ACTIVE);
        requisitiondao.save(requisition);
    }

    @Override
    public void updateStatus(RequisitionStatus status, Requisition requisition) {

    }

    @Override
    public void acknowledge(Requisition requisition) {

    }

    @Override
    public List<Requisition> getRequisitions() {
        this.search = new Search();
        search.addSortDesc("dateCreated");
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            search.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        this.search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return this.requisitiondao.findAll();
    }

    @Override
    public List<Requisition> getRequisitions(Search searchTerm, int offset, int limit) {
        searchTerm.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        searchTerm.setFirstResult(offset);
        searchTerm.setMaxResults(limit);
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            searchTerm.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        searchTerm.addSortDesc("dateCreated");
        return requisitiondao.search(searchTerm);
    }

    @Override
    public Requisition getRequisitionById(String id) {
        return requisitiondao.searchUniqueByPropertyEqual("id", id, RecordStatus.ACTIVE);
    }

    public List<Requisition> getRequisitionsByUserId(User user) {
        this.search = new Search();
        this.search.addFilterEqual("user", user);
        this.search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        this.search.addSortDesc("dateCreated");
//        return requisitiondao.searchUniqueByPropertyEqual("user", user);
        return requisitiondao.search(this.search);
    }

    @Override
    public void deleteRequisition(Requisition requisition) {
        /*
         * Is the logged user having the permission to delete
         */
        requisition.setRecordStatus(RecordStatus.DELETED);
        requisitiondao.delete(requisition);
    }

    @Override
    public List<Requisition> getRequisitionByStatus(RequisitionStatus requisitionStatus) {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addSortDesc("dateCreated");
        search.addFilterEqual("requisitionStatus", requisitionStatus);
        return requisitiondao.searchUnique(search);
    }

    @Override
    public int countRequisitions(Search searchTerm) {
        searchTerm.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            searchTerm.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        return requisitiondao.count(searchTerm);
    }


    public String generateTransactionNo() {
        Search search = new Search();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        Date date = new Date();

        String build = String.valueOf(SharedAppData.getLoggedInUser().getFirstName().toUpperCase().charAt(0)) +
                SharedAppData.getLoggedInUser().getLastName().toUpperCase().charAt(0);
        return "W"+ build + "/" + dateFormat.format(date) + "/" + (withdrawDao.count(search) + 1);
    }


    @Override
    public void changeRequisitionStatus(Account account, Requisition requisition, RequisitionStatus requisitionStatus,
                                        String comment) {
        try {
            User changedBy = requisition.getChangedBy();

            AccountService accountService = ApplicationContextProvider.getBean(AccountService.class);
            WithDrawService drawService = ApplicationContextProvider.getBean(WithDrawService.class);

            int total_account = (int) accountService.getAccountByUser(requisition.getUser()).getOutstandingBalance();

            if (requisitionStatus == RequisitionStatus.ACKNOWLEDGED
                    && (total_account >= requisition.getAmountRequested())) {
                System.out.println("Outstanding Balance = "
                        + accountService.getAccountByUser(requisition.getUser()).getOutstandingBalance());
                System.out.println("Amount Requested = " + requisition.getAmountRequested());

                Double result = accountService.getAccountByUser(requisition.getUser()).getOutstandingBalance()
                        - requisition.getAmountRequested();

                account = accountService.getAccountByUser(requisition.getUser());
                account.setOutstandingBalance(result);
                accountService.save(account);

                Withdraw withdraw = new Withdraw();
                withdraw.setTransactionNumber(generateTransactionNo());
                withdraw.setAmountRequested(requisition.getAmountRequested());
                withdraw.setUser(requisition.getUser());
                drawService.save(withdraw);

                Withdrawing withdrawing = new Withdrawing(requisition.getAmountRequested(), requisition.getUser().getPhoneNumber(), requisition.getUser().getFullName());
                withdrawing.start();

                System.out.println("Acknowledged " + requisition.getUser() + "'s requisition");

                this.message = "Requisition Acknowledged";
            } else if (requisitionStatus == RequisitionStatus.APPROVED
                    && (total_account >= requisition.getAmountRequested())) {
                System.out.println("Approved " + requisition.getUser() + "'s requisition");
                requisition.setRequisitionStatus(requisitionStatus);
                requisitiondao.save(requisition);
                this.message = "Requisition Approved";

            } else if (requisitionStatus == RequisitionStatus.DECLINED) {
                System.out.println("Rejected " + requisition.getUser() + "'s requisition");

                this.message = "Requisition Rejected";
                System.out.println("Requisition Declined");
                requisition.setComment(comment);

//				CustomMailService.sendWelcomeEmail(requisition.getUser(), this.message, comment);
            } else {
                System.out.println("Amount of money not enough");
            }
            CustomMailService thread = new CustomMailService(requisition.getUser(), this.message, comment, requisitionStatus,
                    requisition, changedBy);
            thread.start();
//			CustomMailService.sendWelcomeEmail(requisition.getUser(), this.message, comment, requisitionStatus,requisition);


            requisition.setRequisitionStatus(requisitionStatus);
            requisitiondao.save(requisition);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int countRequisitionsByUser(Search searchTerm, User user) {
        searchTerm.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        searchTerm.addFilterEqual("user", user);
        return requisitiondao.count(searchTerm);
    }

    public int countRequisitionsByStatus(RequisitionStatus requisitionStatus) {
        Search searchTerm = new Search();
        searchTerm.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        searchTerm.addFilterEqual("requisitionStatus", requisitionStatus);
        if (!SharedAppData.getLoggedInUser().hasPermission(CustomPermissionConstants.ACCESS_REQUISITIONS)) {
            searchTerm.addFilterEqual("user", SharedAppData.getLoggedInUser());
        }
        return requisitiondao.count(searchTerm);
    }

    @Override
    public int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        System.out.println("Days Removed: " + getDays(d1, (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
        if (((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
                - getDays(d1, (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))) < 1) {
            return 0;
        }
        return ((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
                - getDays(d1, (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
    }

    public int getDays(Date d1, int days) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        int countDays = 0;
        CalendarService calendarService = ApplicationContextProvider.getBean(CalendarService.class);
        NonWorkingDaysService nonWorkingDaysService = ApplicationContextProvider.getBean(NonWorkingDaysService.class);

        for (int i = 0; i <= days; i++) {
            calendar.setTime(d1);
            calendar.add(java.util.Calendar.DATE, i);
            System.out.println("Date in requisition " + dateFormat.format(calendar.getTime()));

            for (Calendar cal : calendarService.getDates()) {
                int dates = (int) daysBetween(cal.getStartDate(), cal.getEndDate());
                java.util.Calendar calendarDay = java.util.Calendar.getInstance();

                for (int j = 0; j <= dates; j++) {
                    calendarDay.setTime(cal.getStartDate());
                    calendarDay.add(java.util.Calendar.DATE, j);

                    if (dateFormat.format(calendarDay.getTime()).equals(dateFormat.format(calendar.getTime()))) {
                        countDays++;
                        System.out.println("Public Holidays " + dateFormat.format(calendarDay.getTime()));
                    }
                }
            }

            for (NonWorkingDays nonWork : nonWorkingDaysService.getDates()) {
                int dates = (int) daysBetween(nonWork.getStartDate(), nonWork.getEndDate());
                java.util.Calendar calendarDay = java.util.Calendar.getInstance();

                for (int j = 0; j <= dates; j++) {
                    calendarDay.setTime(nonWork.getStartDate());
                    calendarDay.add(java.util.Calendar.DATE, j);

                    if (dateFormat.format(calendarDay.getTime()).equals(dateFormat.format(calendar.getTime()))) {
                        countDays++;
                        System.out.println("Non Working " + dateFormat.format(calendarDay.getTime()));
                    }
                }
            }

        }
        return countDays;

    }

    public long daysBetween(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setViewedRequisition(Requisition requisition) {
        requisition.setView(true);
        requisitiondao.save(requisition);
    }

    @Override
    public int countReq(Search search) {
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("user", SharedAppData.getLoggedInUser());
        return requisitiondao.count(search);
    }

}
