/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.solutions.site.initializer.internal.base;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
public abstract class BaseLayoutSetInitializer
	implements DependenciesInitializer {

	public void initialize(Long groupId) throws Exception {
		_updateLayoutSet(groupId, true);
		_updateLayoutSet(groupId, false);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected LayoutSetLocalService layoutSetLocalService;

	@Reference
	protected LayoutSetService layoutSetService;

	private String _getCSS(boolean privateLayoutSet) throws Exception {
		return read(_getCSSPath(privateLayoutSet));
	}

	private String _getCSSPath(boolean privateLayoutSet) {
		return "/layout-set/" + _getLayoutSetType(privateLayoutSet) +
			"/css.css";
	}

	private String _getLayoutSetType(boolean privateLayoutSet) {
		if (privateLayoutSet) {
			return "private";
		}

		return "public";
	}

	private String _getLogoPath(boolean privateLayoutSet) {
		return "/layout-set/" + _getLayoutSetType(privateLayoutSet) +
			"/logo.png";
	}

	private String _getLookAndFeelJSONPath(boolean privateLayoutSet) {
		return "/layout-set/" + _getLayoutSetType(privateLayoutSet) +
			"/lookAndFeel.json";
	}

	private String _getThemePropertiesPath(boolean privateLayoutSet) {
		return "/layout-set/" + _getLayoutSetType(privateLayoutSet) +
			"/theme.properties";
	}

	private void _updateLayoutSet(Long groupId, boolean privateLayoutSet)
		throws Exception {

		_updateSettings(groupId, privateLayoutSet);
		_updateLookAndFeel(groupId, privateLayoutSet);
		_updateLogo(groupId, privateLayoutSet);
	}

	private void _updateLogo(Long groupId, boolean privateLayoutSet)
		throws Exception {

		ClassLoader classLoader = getClassLoader();

		String filePath =
			getDependenciesPath() + _getLogoPath(privateLayoutSet);

		InputStream inputStream = classLoader.getResourceAsStream(filePath);

		byte[] byteArray = FileUtil.getBytes(inputStream);

		layoutSetService.updateLogo(groupId, privateLayoutSet, true, byteArray);
	}

	private void _updateLookAndFeel(long groupId, boolean privateLayoutSet)
		throws Exception {

		try {
			String lookAndFeelString = read(
				_getLookAndFeelJSONPath(privateLayoutSet));

			if (Validator.isNotNull(lookAndFeelString)) {
				JSONObject lookAndFeelJSONObject = jsonFactory.createJSONObject(
					lookAndFeelString);

				LayoutSet layoutSet = layoutSetLocalService.fetchLayoutSet(
					groupId, privateLayoutSet);

				layoutSetService.updateLookAndFeel(
					groupId, privateLayoutSet,
					lookAndFeelJSONObject.getString("themeId"),
					layoutSet.getColorSchemeId(), _getCSS(privateLayoutSet));
			}
		}
		catch (IOException ioException) {
			_log.info(ioException.getMessage());
		}
	}

	private void _updateSettings(long groupId, boolean privateLayoutSet)
		throws Exception {

		LayoutSet layoutSet = layoutSetLocalService.fetchLayoutSet(
			groupId, privateLayoutSet);

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		UnicodeProperties themeSettingsUnicodeProperties =
			new UnicodeProperties(true);

		themeSettingsUnicodeProperties.fastLoad(
			read(_getThemePropertiesPath(privateLayoutSet)));

		settingsUnicodeProperties.putAll(themeSettingsUnicodeProperties);

		layoutSetService.updateSettings(
			groupId, privateLayoutSet, settingsUnicodeProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseLayoutSetInitializer.class);

}