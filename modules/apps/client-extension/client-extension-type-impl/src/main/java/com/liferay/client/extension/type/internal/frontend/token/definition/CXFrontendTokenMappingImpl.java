/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.frontend.token.definition;

import com.liferay.client.extension.type.frontend.token.definition.FrontendToken;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenMapping;
import com.liferay.client.extension.type.internal.frontend.token.definition.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class CXFrontendTokenMappingImpl implements FrontendTokenMapping {

	public CXFrontendTokenMappingImpl(
			CXFrontendTokenImpl cxFrontendTokenImpl, JSONObject jsonObject) {

		_cxFrontendTokenImpl = cxFrontendTokenImpl;

		CXFrontendTokenDefinitionImpl cxFrontendTokenDefinitionImpl =
			cxFrontendTokenImpl.getFrontendTokenDefinition();

		_jsonLocalizer = cxFrontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

		_type = jsonObject.getString("type");
		_value = jsonObject.getString("value");
	}

	@Override
	public FrontendToken getFrontendToken() {
		return _cxFrontendTokenImpl;
	}

	@Override
	public JSONObject getJSONObject(Locale locale) {
		return _jsonLocalizer.getJSONObject(locale);
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	private final CXFrontendTokenImpl _cxFrontendTokenImpl;
	private final JSONLocalizer _jsonLocalizer;
	private final String _type;
	private final String _value;

}