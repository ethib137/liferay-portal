/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSCETImpl extends BaseCETImpl implements ThemeCSSCET {

	public ThemeCSSCETImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	public ThemeCSSCETImpl(FrontendTokenDefinition frontendTokenDefinition,
						   PortletRequest portletRequest) {
		this(
			StringPool.BLANK,
			frontendTokenDefinition,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"clayURL", ParamUtil.getString(portletRequest, "clayURL")
			).put(
				"frontendTokenDefinition",
				ParamUtil.getString(portletRequest, "frontendTokenDefinition")
			).put(
				"mainURL", ParamUtil.getString(portletRequest, "mainURL")
			).build());
	}

	public ThemeCSSCETImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, FrontendTokenDefinition frontendTokenDefinition,
		String name, Properties properties, String sourceCodeURL,
		UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);

		_frontendTokenDefinition = frontendTokenDefinition;
	}

	public ThemeCSSCETImpl(
		String baseURL, FrontendTokenDefinition frontendTokenDefinition, UnicodeProperties typeSettingsUnicodeProperties) {

		super(baseURL, typeSettingsUnicodeProperties);

		_frontendTokenDefinition = frontendTokenDefinition;
	}

	@Override
	public String getClayURL() {
		return getString("clayURL");
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_theme_css.jsp";
	}

	@Override
	public FrontendTokenDefinition getFrontendTokenDefinition() {
		return _frontendTokenDefinition;
	}

	@Override
	public String getMainURL() {
		return getString("mainURL");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_THEME_CSS;
	}

	@Override
	public boolean hasProperties() {
		return false;
	}

	@Override
	protected boolean isURLCETPropertyName(String name) {
		return _urlCETPropertyNames.contains(name);
	}

	private FrontendTokenDefinition _frontendTokenDefinition;

	private static final Set<String> _urlCETPropertyNames =
		getURLCETPropertyNames(ThemeCSSCET.class);

}