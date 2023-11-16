/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.frontend.token.definition;

import com.liferay.client.extension.type.frontend.token.definition.FrontendToken;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenMapping;
import com.liferay.client.extension.type.frontend.token.definition.FrontendTokenSet;
import com.liferay.client.extension.type.internal.frontend.token.definition.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class CXFrontendTokenImpl implements FrontendToken {

	public CXFrontendTokenImpl(
			CXFrontendTokenSetImpl cxFrontendTokenSetImpl, JSONObject jsonObject) {

		_cxFrontendTokenSetImpl = cxFrontendTokenSetImpl;

		CXFrontendTokenDefinitionImpl cxFrontendTokenDefinitionImpl =
			cxFrontendTokenSetImpl.getFrontendTokenDefinition();

		_jsonLocalizer = cxFrontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

		_name = jsonObject.getString("name");

		_type = Type.parse(jsonObject.getString("type"));

		if (_type == Type.BOOLEAN) {
			_defaultValue = jsonObject.getBoolean("defaultValue");
		}
		else if (_type == Type.INT) {
			_defaultValue = jsonObject.getInt("defaultValue");
		}
		else if (_type == Type.DOUBLE) {
			_defaultValue = jsonObject.getDouble("defaultValue");
		}
		else if (_type == Type.STRING) {
			_defaultValue = jsonObject.getString("defaultValue");
		}
		else {
			throw new RuntimeException(
				"Unsupported frontend token type " + _type);
		}

		JSONArray mappingsJSONArray = jsonObject.getJSONArray("mappings");

		if (mappingsJSONArray == null) {
			return;
		}

		for (int i = 0; i < mappingsJSONArray.length(); i++) {
			FrontendTokenMapping frontendTokenMapping =
				new CXFrontendTokenMappingImpl(
					this, mappingsJSONArray.getJSONObject(i));

			_frontendTokenMappings.add(frontendTokenMapping);

			List<FrontendTokenMapping> frontendTokenMappings =
				_frontendTokenMappingsMap.computeIfAbsent(
					frontendTokenMapping.getType(), type -> new ArrayList<>());

			frontendTokenMappings.add(frontendTokenMapping);
		}
	}

	@Override
	public <T> T getDefaultValue() {
		return (T)_defaultValue;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return _frontendTokenMappings;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings(
		String type) {

		return _frontendTokenMappingsMap.get(type);
	}

	@Override
	public FrontendTokenSet getFrontendTokenSet() {
		return _cxFrontendTokenSetImpl;
	}

	@Override
	public JSONObject getJSONObject(Locale locale) {
		return _jsonLocalizer.getJSONObject(locale);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Type getType() {
		return _type;
	}

	protected CXFrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _cxFrontendTokenSetImpl.getFrontendTokenDefinition();
	}

	private final Object _defaultValue;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final Map<String, List<FrontendTokenMapping>>
		_frontendTokenMappingsMap = new HashMap<>();
	private final CXFrontendTokenSetImpl _cxFrontendTokenSetImpl;
	private final JSONLocalizer _jsonLocalizer;
	private final String _name;
	private final Type _type;

}