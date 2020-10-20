package org.primefaces.california;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.pahappa.systems.core.services.RequisitionService;
import org.pahappa.systems.core.services.WithDrawService;
import org.pahappa.systems.core.services.impl.WithdrawServiceImpl;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.pahappa.systems.models.Withdraw;
import org.primefaces.model.chart.*;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

import com.googlecode.genericdao.search.Search;

@ManagedBean
@RequestScoped
public class ChartView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private HorizontalBarChartModel horizontalBarModel;
    private HorizontalBarChartModel horizontalBarModel1;

    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    WithDrawService withDrawService = ApplicationContextProvider.getBean(WithDrawService.class);


    RequisitionService requisitionService = ApplicationContextProvider.getBean(RequisitionService.class);
    UserService userService = ApplicationContextProvider.getBean(UserService.class);

    @PostConstruct
    public void init() {
        createBarModels();
        createPieModels();
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    private void createPieModels() {
        createPieModel1();
        createPieModel2();
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        pieModel1.setTitle("Requisitions");

        User user = SharedAppData.getLoggedInUser();

        pieModel1.set("Pending", this.requisitionService.countRequisitionsByStatus(RequisitionStatus.PENDING));
        pieModel1.set("Approved", this.requisitionService.countRequisitionsByStatus(RequisitionStatus.APPROVED));
        pieModel1.set("Rejected", this.requisitionService.countRequisitionsByStatus(RequisitionStatus.DECLINED));
        pieModel1.set("Acknowledged", this.requisitionService.countRequisitionsByStatus(RequisitionStatus.ACKNOWLEDGED));



        pieModel1.setLegendPosition("e");
        pieModel1.setShadow(false);

    }
    private void createPieModel2() {
        pieModel2 = new PieChartModel();
        pieModel2.setTitle("Food Expense");

        User user = SharedAppData.getLoggedInUser();

        pieModel2.set("1st Qtr", expensesPerQuarter(1));
        pieModel2.set("2nd Qtr", expensesPerQuarter(2));
        pieModel2.set("3rd Qtr", expensesPerQuarter(3));
        pieModel2.set("4th Qtr", expensesPerQuarter(4));



        pieModel2.setLegendPosition("e");
        pieModel2.setShadow(false);

    }


    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
    public HorizontalBarChartModel getHorizontalBarModel1() {
        return horizontalBarModel1;
    }

    private void createBarModels() {
        horizontalBarModel = new HorizontalBarChartModel();

        horizontalBarModel1 = new HorizontalBarChartModel();

        ChartSeries approved = new ChartSeries();
        approved.setLabel("Approved Requisitions");
        dataRecord(approved, RequisitionStatus.APPROVED);

        ChartSeries rejected = new ChartSeries();
        rejected.setLabel("Rejected Requisitions");
        dataRecord(rejected, RequisitionStatus.DECLINED);

        ChartSeries ack = new ChartSeries();
        ack.setLabel("Acknowledged Requisitions");
        dataRecord(ack, RequisitionStatus.ACKNOWLEDGED);


        ChartSeries expenses = new ChartSeries();
        expenses.setLabel("Expenses");
        dataRecorded(expenses);


        horizontalBarModel.setTitle("Requisitions per employee");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);

        horizontalBarModel1.setTitle("Expense per Vendor");
        horizontalBarModel1.setLegendPosition("e");
        horizontalBarModel1.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Requisitions");
        xAxis.setMin(0);

        Axis xAxiss = horizontalBarModel1.getAxis(AxisType.X);
        xAxiss.setLabel("Expenses");
        xAxiss.setMin(0);
//		xAxis.setMax(200);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Employees");

        Axis yAxiss = horizontalBarModel1.getAxis(AxisType.Y);
        yAxiss.setLabel("Employees");
    }

    public void dataRecord(ChartSeries series, RequisitionStatus requisitionStatus) {
        try {
            List<User> users = this.userService.getUsers();
            for (User user : users) {
                if (!user.hasAdministrativePrivileges()) {
                    Search search = new Search();
                    search.addFilterEqual("requisitionStatus", requisitionStatus);
                    if (this.requisitionService.countRequisitionsByUser(search, user) < 1) {
                        series.set(user.getLastName(), 0);
                    } else {
                        series.set(user.getLastName(), this.requisitionService.countRequisitionsByUser(search, user));
                    }

                }
            }
            horizontalBarModel.addSeries(series);

        } catch (OperationFailedException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }


    public void dataRecorded(ChartSeries series) {
        try {
            List<User> users = this.userService.getUsers();
            int count = 0;
            for (User user : users) {
                if (!user.hasAdministrativePrivileges()) {
                    for(Withdraw withdraw : this.withDrawService.getTransactionsByUser(user)){
                        series.set(user.getLastName(), withdraw.getAmountRequested());
                        System.out.println("Amount Requested "+user.getLastName()+" : "+ withdraw.getAmountRequested());
                    }
                }

            }
            horizontalBarModel1.addSeries(series);

        } catch (OperationFailedException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public int expensesPerQuarter(int quarter){
        int count = 0;
        for(Withdraw withdraw : this.withDrawService.getTransactions()){
            if(getQuarter(withdraw.getDateCreated()) == quarter){
                count+=withdraw.getAmountRequested();
            }
        }
        return count;
    }

    /*public void dataRecorded(ChartSeries series, int quarter) {
        try {
            List<User> users = this.userService.getUsers();
            int count = 0;
            for (User user : users) {
                if (!user.hasAdministrativePrivileges()) {
                    for(Withdraw withdraw : this.withDrawService.getTransactionsByUser(user)){
                        if(quarter == getQuarter(withdraw.getDateCreated())){

                            count++;
                            System.out.println("The quarter "+quarter);
                        }
                    }
                }
                series.set(user.getLastName(), count);
            }

            horizontalBarModel1.addSeries(series);

        } catch (OperationFailedException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }*/

    public int getQuarter(Date date){
        Calendar cal = Calendar.getInstance(Locale.US);
        /* Consider whether you need to set the calendar's timezone. */
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH); /* 0 through 11 */
        return (month / 3) + 1;
    }
}