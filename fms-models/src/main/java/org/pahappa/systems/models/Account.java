/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pahappa.systems.models;


import javax.persistence.*;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;


@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
public class Account extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private double outstandingBalance;
    private double workingDays;
    private double entitledAllowance;
    private User user = new User();

    @Column(name = "entitled_allowance")
    public double getEntitledAllowance() {
        return entitledAllowance;
    }

    

    @JoinColumn(name="user")
    @OneToOne
    public User getUser() {
        return user;
    }


    @Column(name = "outstanding_balance")
    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    @Column(name = "working_days")
    public double getWorkingDays() {
        return workingDays;
    }

    public void setEntitledAllowance(double entitledAllowance) {
        this.entitledAllowance = entitledAllowance;
    }
    
    
     
    public void setUser(User user) {
        this.user = user;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public void setWorkingDays(double workingDays) {
        this.workingDays = workingDays;
    }
    
    
}
