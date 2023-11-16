/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.client.extension.type.internal.ThemeCSSCETImpl;
import com.liferay.client.extension.type.internal.frontend.token.definition.CXFrontendTokenDefinitionImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Reference;

import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSCETImplFactoryImpl implements CETImplFactory<ThemeCSSCET> {

	@Override
	public ThemeCSSCET create(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		// TODO what about this?
		return new ThemeCSSCETImpl(clientExtensionEntry);
	}

	@Override
	public ThemeCSSCET create(PortletRequest portletRequest)
		throws PortalException {

		String frontendTokenDefinitionString = ParamUtil.getString(
				portletRequest, "frontendTokenDefinition");

		FrontendTokenDefinition frontendTokenDefinition = null;

		if (frontendTokenDefinitionString != null) {
			frontendTokenDefinition = _getFrontendTokenDefinition(
					null, frontendTokenDefinitionString);
		}

		return new ThemeCSSCETImpl(frontendTokenDefinition, portletRequest);
	}

	@Override
	public ThemeCSSCET create(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL, UnicodeProperties unicodeProperties)
		throws PortalException {

		String frontendTokenDefinitionString = unicodeProperties.get(
				"frontendTokenDefinition");

		FrontendTokenDefinition frontendTokenDefinition = null;

		if (frontendTokenDefinitionString != null) {
			frontendTokenDefinition = _getFrontendTokenDefinition(
					name, frontendTokenDefinitionString);
		}

		return new ThemeCSSCETImpl(
			baseURL, companyId, description, externalReferenceCode,
			frontendTokenDefinition, name, properties, sourceCodeURL,
			unicodeProperties);
	}

	private FrontendTokenDefinition _getFrontendTokenDefinition(
			String name, String frontendTokenDefinitionString) {
		try {
			return new CXFrontendTokenDefinitionImpl(
					JSONFactoryUtil.createJSONObject(
									frontendTokenDefinitionString),
					JSONFactoryUtil.getJSONFactory(),
					ResourceBundleLoaderUtil.
									getPortalResourceBundleLoader(),
					name);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		String frontendTokenDefinitionString = newTypeSettingsUnicodeProperties.get(
				"frontendTokenDefinition");

		FrontendTokenDefinition frontendTokenDefinition = null;

		if (frontendTokenDefinitionString != null) {
			frontendTokenDefinition = _getFrontendTokenDefinition(
					null, frontendTokenDefinitionString);
		}

		ThemeCSSCET newThemeCSSCET = new ThemeCSSCETImpl(
			StringPool.BLANK, frontendTokenDefinition, newTypeSettingsUnicodeProperties);

		String baseURL = newThemeCSSCET.getBaseURL();

		if (!Validator.isBlank(baseURL) && !Validator.isUrl(baseURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid base URL: " + baseURL, "base-url-x-is-invalid",
				baseURL);
		}

		String clayURL = newThemeCSSCET.getClayURL();

		if (!Validator.isBlank(clayURL) && !Validator.isUrl(clayURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid Clay CSS URL: " + clayURL, "clay-css-url-x-is-invalid",
				clayURL);
		}

		String mainURL = newThemeCSSCET.getMainURL();

		if (!Validator.isBlank(mainURL) && !Validator.isUrl(mainURL, true)) {
			throw new ClientExtensionEntryTypeSettingsException(
				"Invalid Main CSS URL: " + mainURL, "main-css-url-x-is-invalid",
				mainURL);
		}
	}

}