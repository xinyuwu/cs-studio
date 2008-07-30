package org.csstudio.nams.service.preferenceservice.declaration;

public enum PreferenceServiceDatabaseKeys implements HoldsAPreferenceId {
	P_CONFIG_DATABASE_CONNECTION(
			"org.csstudio.ams.preferences.configDatabaseConnection",
			"Configuration databases jdbc-url"),

	P_CONFIG_DATABASE_TYPE("org.csstudio.nams.preferences.configDatabaseType",
			"Type of config database"), P_CONFIG_DATABASE_USER(
			"org.csstudio.ams.preferences.configDatabaseUser",
			"Configuration databases user"), P_CONFIG_DATABASE_PASSWORD(
			"org.csstudio.ams.preferences.configDatabasePassword",
			"Configuration databases password"),

	P_APP_DATABASE_CONNECTION(
			"org.csstudio.ams.preferences.appDatabaseConnection",
			"Application databases jdbc-url"), P_APP_DATABASE_USER(
			"org.csstudio.ams.preferences.appDatabaseUser",
			"Application databases user"), P_APP_DATABASE_PASSWORD(
			"org.csstudio.ams.preferences.appDatabasePassword",
			"Application databases password"), P_APP_DATABASE_TYPE(
			"org.csstudio.nams.preferences.appDatabaseType",
			"Type of application database");

	private String _key;
	private String _description;

	private PreferenceServiceDatabaseKeys(final String key) {
		this._key = key;
		this._description = "No description available";
	}

	private PreferenceServiceDatabaseKeys(final String key,
			final String description) {
		this._key = key;
		this._description = description;
	}

	public String getDescription() {
		return this._description;
	}

	public String getKey() {
		return this._key;
	}

	public String getPreferenceStoreId() {
		return this._key;
	}

}
