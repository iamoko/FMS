package org.pahappa.systems.views.settings;

import org.primefaces.PrimeFaces;
import javax.faces.application.FacesMessage;

public class MessageComposer {
    public static void  Compose(String title, String description){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title,description);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
    public static void  Failed(String title, String description){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, title,description);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
}
