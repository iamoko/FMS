package org.pahappa.systems.client.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.sers.webutils.model.Country;
import org.sers.webutils.server.core.service.SetupService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;


@FacesConverter("countryConverter")
public class CountryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String countryId) {
		if (countryId == null || countryId.isEmpty())
			return null;
		return ApplicationContextProvider.getBean(SetupService.class).getCountryById(countryId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object country) {
		if (country == null || !(country instanceof Country))
			return null;
		return ((Country) country).getId();
	}
}
