package org.pahappa.systems.core.settings;

import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.security.service.UserSessionInit;
import org.sers.webutils.server.core.security.service.impl.CustomSessionProvider;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Scope("session")
public class UserSessionInitImpl implements UserSessionInit {

	@Override
	public void onSessionCreated() {
		User loggedIn = ApplicationContextProvider.getBean(CustomSessionProvider.class).getLoggedInUser();
		System.out.println("Acquired logged in user: " + loggedIn);
	}
}
