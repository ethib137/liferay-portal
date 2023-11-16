/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.frontend.token.definition;

import com.liferay.client.extension.type.frontend.token.definition.FrontendToken;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenCategory;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenMapping;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenSet;
import com.liferay.client.extension.type.internal.frontend.token.definition.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class CXFrontendTokenCategoryImpl implements FrontendTokenCategory {

	public CXFrontendTokenCategoryImpl(
		CXFrontendTokenDefinitionImpl cxFrontendTokenDefinitionImpl,
		JSONObject jsonObject) {

		_cxFrontendTokenDefinitionImpl = cxFrontendTokenDefinitionImpl;

		_jsonLocalizer = cxFrontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

		JSONArray frontendTokenSetsJSONArray = jsonObject.getJSONArray(
			"frontendTokenSets");

		if (frontendTokenSetsJSONArray == null) {
			return;
		}

		for (int i = 0; i < frontendTokenSetsJSONArray.length(); i++) {
			FrontendTokenSet frontendTokenSet = new CXFrontendTokenSetImpl(
				this, frontendTokenSetsJSONArray.getJSONObject(i));

			_frontendTokenMappings.addAll(
				frontendTokenSet.getFrontendTokenMappings());

			_frontendTokens.addAll(frontendTokenSet.getFrontendTokens());

			_frontendTokenSets.add(frontendTokenSet);
		}
	}

	@Override
	public CXFrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _cxFrontendTokenDefinitionImpl;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return _frontendTokenMappings;
	}

	@Override
	public Collection<FrontendToken> getFrontendTokens() {
		return _frontendTokens;
	}

	@Override
	public Collection<FrontendTokenSet> getFrontendTokenSets() {
		return _frontendTokenSets;
	}

	@Override
	public JSONObject getJSONObject(Locale locale) {
		return _jsonLocalizer.getJSONObject(locale);
	}

	private final CXFrontendTokenDefinitionImpl _cxFrontendTokenDefinitionImpl;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Collection<FrontendToken> _frontendTokens = new ArrayList<>();
	private final Collection<FrontendTokenSet> _frontendTokenSets =
		new ArrayList<>();
	private final JSONLocalizer _jsonLocalizer;

}