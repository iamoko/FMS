package org.primefaces.california;

import java.net.URL;

public class ResourceURLProvider {
	private static String KHANS_LOGO_NAME = "actv.jpg";

	public URL getKhansLogo() {
		return getClass().getResource(KHANS_LOGO_NAME);
	}

	private ResourceURLProvider() {

	}

	public static class Util {

		private static ResourceURLProvider instance;

		public static ResourceURLProvider getInstance() {
			if (instance == null)
				instance = new ResourceURLProvider();

			return instance;
		}

		private Util() {

		}
	}
}
