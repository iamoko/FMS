package org.pahappa.systems.models;

import java.util.Date;
import javax.persistence.*;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

@Entity
@Table(name = "non_working_days")
@Inheritance(strategy = InheritanceType.JOINED)
public class NonWorkingDays extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private User user = new User();
    private Date startDate;
    private Date endDate;

    /**
     * @return the user
     */
    @JoinColumn(name="user")
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


    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Override
    public String toString() {
        return "CalendarSpecific{" +
                "user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

