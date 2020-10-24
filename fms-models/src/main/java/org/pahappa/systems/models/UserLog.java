package org.pahappa.systems.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

@Entity
@Table(name="User_logs")
public class UserLog extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String action;
	private Date dateLogged;
	private User user;
	
	@Column(name="action", length=2000)
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_logged")
	public Date getdateLogged() {
		return dateLogged;
	}
	
	
	public void setdateLogged(Date dateLogged) {
		this.dateLogged = dateLogged;
	}
    
	@ManyToOne
	@JoinColumn(name="user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
