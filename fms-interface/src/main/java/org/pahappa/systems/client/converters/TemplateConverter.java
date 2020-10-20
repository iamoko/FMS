package org.pahappa.systems.client.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.pahappa.systems.models.TemplateType;
@FacesConverter("templateConverter")
public class TemplateConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return TemplateType.getConstantFromString(s);
    }
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o instanceof TemplateType)
            return  o.toString();
        return null;
    }
}