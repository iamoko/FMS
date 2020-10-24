package org.pahappa.systems.models;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "requisition_per_day")
@Inheritance(strategy = InheritanceType.JOINED)
public class RequisitionPerDay extends BaseEntity {

    private static final long serialVersionUID = 1L;


    private Date day;
    private String requisitionNumber;
    private User user = new User();

    /**
     * @return the requisitionNumber
     */
    @Column(name = "req_no", length = 1000, nullable = true)
    public String getRequisitionNumber() {
        return requisitionNumber;
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

    @Column(name = "day")
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    //        @Temporal(TemporalType.TIMESTAMP)
//        @Column(name = "status_updated_date")
//        public Date getStatusUpdatedDate() {
//                return statusUpdatedDate;
//        }


    /**
     * @param requisitionNumber the requisitionNumber to set
     */
    public void setRequisitionNumber(String requisitionNumber) {
        this.requisitionNumber = requisitionNumber;
    }

    @Override
    public String toString() {
        return "RequisitionPerDay{" +
                "day=" + day +
                ", requisitionNumber='" + requisitionNumber + '\'' +
                ", user=" + user +
                '}';
    }
}
