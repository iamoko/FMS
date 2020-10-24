package org.pahappa.systems.views.security;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.views.home.HomePage;
import org.pahappa.systems.views.settings.MessageComposer;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

/**
 * 
 * @author Eng.Ivan
 * 
 */
@ManagedBean(name = "profileView", eager = true)
@SessionScoped
@ViewPath(path = HyperLinks.USERPROFILE)
public class ProfileView extends WebFormView<User, ProfileView, HomePage> {
	private static final long serialVersionUID = 1L;

	public ProfileView() {
	}

	@Override
	public void persist() throws Exception {
		MessageComposer.Compose("Action Successful","Account Details Updated");
		ApplicationContextProvider.getApplicationContext()
				.getBean(UserService.class).saveUser(super.getModel());
	}

	@Override
	public void resetModal() {
		super.model = SharedAppData.getLoggedInUser();
	}

	@Override
	@PostConstruct
	public void beanInit() {
		super.model = SharedAppData.getLoggedInUser();
	}

	@Override
	public void pageLoadInit() {

	}
}