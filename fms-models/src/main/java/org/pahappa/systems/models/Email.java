package org.pahappa.systems.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

@Entity
@Table(name="emails")
public class Email extends BaseEntity {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private String message, heading;
	private User user;

	@Column(name ="head",length=30000)
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	@Column(name ="message",length=30000)
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
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

