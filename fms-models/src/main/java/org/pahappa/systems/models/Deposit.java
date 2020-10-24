package org.pahappa.systems.models;

import javax.persistence.*;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

import java.util.Date;

@Entity
@Table(name = "deposit")
public class Deposit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private User user;
    private int amount;
    private Date depositDate;
    private String transactionNumber;

    @Column(name = "trans_no")
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Column(name = "deposit_date")
    public Date getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    /**
     * @return the amount
     */
    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
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
        return "Deposit{" +
                "user=" + user +
                ", amount=" + amount +
                ", depositDate=" + depositDate +
                '}';
    }
}
