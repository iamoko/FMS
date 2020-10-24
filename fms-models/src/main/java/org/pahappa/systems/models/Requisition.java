package org.pahappa.systems.models;

import java.util.Date;

import javax.persistence.*;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

@Entity
@Table(name = "requisition")
@Inheritance(strategy = InheritanceType.JOINED)
public class Requisition extends BaseEntity {

        private static final long serialVersionUID = 1L;

        private int amountRequested;
        private int daysRequested;
        private String comment;
        private Date startDate;
        private Date endDate;
        private String requisitionNumber;
        private User user = new User();
        private RequisitionStatus requisitionStatus;
        private Boolean view;

        @Column(name = "viewed", length = 1000, nullable = true)
        public Boolean getView() {
			return view;
		}

        /**
		 * @return the requisitionNumber
		 */
        @Column(name = "req_no", length = 1000, nullable = true)
		public String getRequisitionNumber() {
			return requisitionNumber;
		}


		@Column(name = "start_date")
        public Date getStartDate() {
                return startDate;
        }
        
        @Column(name = "end_date")
        public Date getEndDate() {
                return endDate;
        }



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

		@Column(name = "amount_requested")
        public int getAmountRequested() {
                return amountRequested;
        }

        @Column(name = "days_requested")
        public int getDaysRequested() {
                return daysRequested;
        }

        @Column(name = "comment", length = 1000, nullable = true)
        public String getComment() {
                return comment;
        }

//        @Temporal(TemporalType.TIMESTAMP)
//        @Column(name = "status_updated_date")
//        public Date getStatusUpdatedDate() {
//                return statusUpdatedDate;
//        }

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "status")
        public RequisitionStatus getRequisitionStatus() {
                return requisitionStatus;
        }
        
		public void setView(Boolean view) {
			this.view = view;
		}


		/**
		 * @param requisitionNumber the requisitionNumber to set
		 */
		public void setRequisitionNumber(String requisitionNumber) {
			this.requisitionNumber = requisitionNumber;
		}
        public void setStartDate(Date startDate) {
                this.startDate = startDate;
        }
        
        public void setEndDate(Date endDate) {
                this.endDate = endDate;
        }
        
        public void setAmountRequested(int amountRequested) {
                this.amountRequested = amountRequested;
        }

        public void setDaysRequested(int daysRequested) {
                this.daysRequested = daysRequested;
        }

        public void setComment(String comment) {
                this.comment = comment;
        }



        public void setRequisitionStatus(RequisitionStatus requisitionStatus) {
                this.requisitionStatus = requisitionStatus;
        }
}
