package org.pahappa.systems.models;

import java.util.Date;
import javax.persistence.*;
import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "calendar")
@Inheritance(strategy = InheritanceType.JOINED)
public class Calendar extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String comment;
    private Date startDate;
    private Date endDate;
    
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


    @Column(name = "comment", length = 1000)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


	@Override
	public String toString() {
		return "Calendar [comment=" + comment + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
    

}

