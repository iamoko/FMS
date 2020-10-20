package org.pahappa.systems.client.converters;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ConverterConstants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String ALL_PARAM = "-- All --";
	private String allValues = ALL_PARAM;

	/**
	 * @return the allValues
	 */
	public String getAllValues() {
		return allValues;
	}

	/**
	 * @param allValues the allValues to set
	 */
	public void setAllValues(String allValues) {
		this.allValues = allValues;
	}

}
