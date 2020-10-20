package org.pahappa.systems.views.account;

import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.core.services.NonWorkingDaysService;
import org.pahappa.systems.core.services.impl.CustomMailService;
import org.pahappa.systems.core.services.impl.CustomizedMailService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.models.NonWorkingDays;
import org.pahappa.systems.views.StoreLogs;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreditAccounts extends Thread {
    public Date dateFrom, dateTo;

    public CreditAccounts(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public void run() {
        getCollidingDates(this.dateFrom, this.dateTo);

    }

    public static void getCollidingDates(Date d1, Date d2) {

        DepositService depositService = ApplicationContextProvider.getBean(DepositService.class);


        for (Account acc : getAccounts()) {
            int creditedAmount = 0;
            long diff = daysBetween(d1, d2);
            List<List> dates = processDates(d1, (int) diff, acc);
            List<Date> collidingDates = dates.get(0);
            List<Date> allDates = dates.get(1);
            for (Date colliding : collidingDates) {
                if (allDates.contains(colliding)) {
                    allDates.remove(allDates.indexOf(colliding));
                }
            }

            for (Date nonColiding : allDates) {
                /*Handling the account*/
                AccountService accountService = ApplicationContextProvider.getBean(AccountService.class);
                Account account = accountService.getAccountByUser(acc.getUser());
                account.setOutstandingBalance(account.getOutstandingBalance() + account.getEntitledAllowance());
                accountService.save(account);

                /*Handling the Deposits*/
                Deposit deposit = new Deposit();
                deposit.setUser(acc.getUser());
                deposit.setTransactionNumber(generateTransactionNo(acc.getUser()));
                deposit.setDepositDate(nonColiding);
                creditedAmount = (int) acc.getEntitledAllowance();
                deposit.setAmount((int) acc.getEntitledAllowance());
                StoreLogs.Log("Deposited " + (int) acc.getEntitledAllowance() + " on " + acc.getUser().getFullName() + "'s account for " + nonColiding);

                depositService.save(deposit);
            }
            System.out.println("Non Collidong Dates" + allDates);
            CustomizedMailService customizedMailService = new CustomizedMailService(acc.getUser(), "Account Credited", "Your Account has been Credited with Shs."+creditedAmount);
            customizedMailService.start();
        }


    }

    public static long daysBetween(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String generateTransactionNo(User user) {
        DepositService depositService = ApplicationContextProvider.getBean(DepositService.class);
        Search search = new Search();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        StringBuilder build = new StringBuilder();
        build.append(user.getFirstName().toUpperCase().charAt(0));
        build.append(user.getLastName().toUpperCase().charAt(0));
        return "D"+build.toString() + "/" + dateFormat.format(new Date()) + "/" + ( depositService.countDeposits() + 1);
    }

    public static List processDates(Date d1, int days, Account account) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        List<Date> collidingDates = new ArrayList<Date>();
        List<Date> selectedDateRange = new ArrayList<Date>();
        List<List> bothCalendars = new ArrayList<List>();
        CalendarService calendarService = ApplicationContextProvider.getBean(CalendarService.class);
        NonWorkingDaysService nonWorkingDaysService = ApplicationContextProvider.getBean(NonWorkingDaysService.class);
        int counted = 0;
        System.out.println("User: " + account.getUser().getFullName());
        for (int i = 0; i <= days; i++) {
            calendar.setTime(d1);
            calendar.add(java.util.Calendar.DATE, i);
            selectedDateRange.add(calendar.getTime());
            for (Calendar cal : calendarService.getDates()) {
                int dates = (int) daysBetween(cal.getStartDate(), cal.getEndDate());
                java.util.Calendar calendarDay = java.util.Calendar.getInstance();
                for (int j = 0; j <= dates; j++) {
                    calendarDay.setTime(cal.getStartDate());
                    calendarDay.add(java.util.Calendar.DATE, j);
                    if (dateFormat.format(calendarDay.getTime()).equals(dateFormat.format(calendar.getTime()))) {
                        collidingDates.add(calendarDay.getTime());
                        System.out.println("Public Holidays " + dateFormat.format(calendarDay.getTime()));
                    }
                }
            }


            for (NonWorkingDays nonWork : nonWorkingDaysService.getCalendar(account.getUser())) {
                int dates = (int) daysBetween(nonWork.getStartDate(), nonWork.getEndDate());
                java.util.Calendar calendarDay = java.util.Calendar.getInstance();

                for (int j = 0; j <= dates; j++) {
                    calendarDay.setTime(nonWork.getStartDate());
                    calendarDay.add(java.util.Calendar.DATE, j);
                    if (dateFormat.format(calendarDay.getTime()).equals(dateFormat.format(calendar.getTime()))) {
                        collidingDates.add(calendarDay.getTime());
                        counted++;
//                        System.out.println("Non Working " + dateFormat.format(calendarDay.getTime()));
                    }
                }
            }
        }
        System.out.println("Non working Days :" + counted);
        bothCalendars.add(collidingDates);
        bothCalendars.add(selectedDateRange);
        return bothCalendars;
    }

    public static List<Account> getAccounts() {
        AccountService accountService = ApplicationContextProvider.getBean(AccountService.class);
        return accountService.getAccounts();
    }
}
