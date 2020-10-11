package org.pahappa.systems.views.account;

import org.pahappa.systems.core.API.Depositing;
import org.pahappa.systems.core.services.AccountService;
import org.pahappa.systems.core.services.CalendarService;
import org.pahappa.systems.core.services.DepositService;
import org.pahappa.systems.core.services.NonWorkingDaysService;
import org.pahappa.systems.core.services.impl.CustomizedMailService;
import org.pahappa.systems.models.Account;
import org.pahappa.systems.models.Calendar;
import org.pahappa.systems.models.Deposit;
import org.pahappa.systems.models.NonWorkingDays;
import org.pahappa.systems.views.StoreLogs;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.core.utils.DateUtils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class CheckDeposited extends Thread {
    public Date dateFrom, dateTo;

    public CheckDeposited(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public void run() {
        int totalDeposit = 0;
        for (Account account : getAccounts()) {
            totalDeposit += dateProcessing(this.dateFrom, this.dateTo, account);
        }
        Depositing depositing = new Depositing(totalDeposit);
        depositing.start();
    }

    public int dateProcessing(Date d1, Date d2, Account account) {
        return processDating(d1, d2, account);
    }

    public static int processDating(Date d1, Date d2, Account account) {
        List<Date> collidingDates = new ArrayList<>();
        List<Date> allDates = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar calendarDay = java.util.Calendar.getInstance();
        java.util.Calendar calendarDate = java.util.Calendar.getInstance();

        System.out.println("User : " + account.getUser().getFullName());
        for (Date date : DateUtils.getDates(d1, d2, 1)) {
            calendarDay.setTime(date);
            allDates.add(calendarDay.getTime());
            for (Deposit deposit : getDeposits(account.getUser())) {
                calendarDate.setTime(deposit.getDepositDate());
                if (dateFormat.format(calendarDate.getTime()).equals(dateFormat.format(calendarDay.getTime()))) {
                    collidingDates.add(calendarDate.getTime());
                    System.out.println("Deposit Colliding Date : " + calendarDate.getTime());
                }
            }
            for (Calendar calendar : getCalendarDates()) {
                for (Date date1 : DateUtils.getDates(calendar.getStartDate(), calendar.getEndDate(), 1)) {
                    calendarDate.setTime(date1);
                    if (dateFormat.format(calendarDate.getTime()).equals(dateFormat.format(calendarDay.getTime()))) {
                        collidingDates.add(calendarDate.getTime());
                        System.out.println("Holiday Colliding Date : " + calendarDate.getTime());
                    }
                }
            }
            for (NonWorkingDays nonWorkingDays : getNonworkingDates(account.getUser())) {
                for (Date date1 : DateUtils.getDates(nonWorkingDays.getStartDate(), nonWorkingDays.getEndDate(), 1)) {
                    calendarDate.setTime(date1);
                    if (dateFormat.format(calendarDate.getTime()).equals(dateFormat.format(calendarDay.getTime()))) {
                        collidingDates.add(calendarDate.getTime());
                        System.out.println("Nonworking Colliding Date : " + calendarDate.getTime());
                    }
                }
            }

        }

        for (Date colliding : collidingDates) {
            allDates.remove(colliding);
        }
        System.out.println(account.getUser().getFullName() + " - All Free Dates : " + allDates);
        return saveDeposit(allDates, account);
    }

    public static int saveDeposit(List<Date> allDates, Account account) {
        int creditedAmount = 0;
        DepositService depositService = ApplicationContextProvider.getBean(DepositService.class);
        AccountService accountService = ApplicationContextProvider.getBean(AccountService.class);
        for (Date nonColiding : allDates) {
            /*Handling the account*/
            account.setOutstandingBalance(account.getOutstandingBalance() + account.getEntitledAllowance());
            accountService.save(account);

            /*Handling the Deposits*/
            Deposit deposit = new Deposit();
            deposit.setUser(account.getUser());
            deposit.setTransactionNumber(generateTransactionNo(account.getUser()));
            deposit.setDepositDate(nonColiding);
            creditedAmount += (int) account.getEntitledAllowance();
            deposit.setAmount((int) account.getEntitledAllowance());
            StoreLogs.Log("Deposited " + (int) account.getEntitledAllowance() + " on " + account.getUser().getFullName() + "'s account for " + nonColiding);
            depositService.save(deposit);
        }
        CustomizedMailService customizedMailService = new CustomizedMailService(account.getUser(), "Account Credited", "Your Account has been Credited with Shs." + creditedAmount);
        customizedMailService.start();
        return creditedAmount;
    }

    public static String generateTransactionNo(User user) {
        DepositService depositService = ApplicationContextProvider.getBean(DepositService.class);
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String build = String.valueOf(user.getFirstName().toUpperCase().charAt(0)) +
                user.getLastName().toUpperCase().charAt(0);
        return "D" + build + "/" + dateFormat.format(new Date()) + "/" + (depositService.countDeposits() + 1);
    }

    public static List<Calendar> getCalendarDates() {
        return ApplicationContextProvider.getBean(CalendarService.class).getDates();
    }

    public static List<NonWorkingDays> getNonworkingDates(User user) {
        return ApplicationContextProvider.getBean(NonWorkingDaysService.class).getCalendar(user);
    }

    public static List<Deposit> getDeposits(User user) {
        return ApplicationContextProvider.getBean(DepositService.class).getTransactionsByUser(user);
    }

    public static List<Account> getAccounts() {
        return ApplicationContextProvider.getBean(AccountService.class).getAccounts();
    }
}