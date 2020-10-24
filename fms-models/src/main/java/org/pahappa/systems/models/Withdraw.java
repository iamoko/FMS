package org.pahappa.systems.models;

import javax.persistence.*;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

import java.util.Date;

@Entity
@Table(name = "withdraw")
public class Withdraw extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private User user;
    private Date day;
    private int amountRequested;
    private String transactionNumber;

    @Column(name = "date_requested")
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @Column(name = "trans_no")
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * @return the amountRequested
     */
    @Column(name = "amount")
    public int getAmountRequested() {
        return amountRequested;
    }

    /**
     * @param amountRequested the amountRequested to set
     */
    public void setAmountRequested(int amountRequested) {
        this.amountRequested = amountRequested;
    }

    /**
     * @return the user
     */
    @JoinColumn(name = "user")
    @OneToOne
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Withdraw{" +
                "user=" + user +
                ", day=" + day +
                ", amountRequested=" + amountRequested +
                ", transactionNumber='" + transactionNumber + '\'' +
                '}';
    }
}
