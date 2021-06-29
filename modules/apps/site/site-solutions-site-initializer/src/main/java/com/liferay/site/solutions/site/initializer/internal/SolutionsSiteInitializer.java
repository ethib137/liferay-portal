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

package com.liferay.site.solutions.site.initializer.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + SolutionsSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class SolutionsSiteInitializer implements SiteInitializer {

	public static final String KEY = "site-solutions-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return "Solutions";
	}

	@Override
	public String getThumbnailSrc() {
		return null;
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			Map<String, String> fileEntriesMap =
				_solutionsDLFileEntriesInitializer.initialize(
					groupId, "images");

			_solutionsFragmentsInitializer.initialize(groupId);

			_solutionsStylebookInitializer.initialize(groupId);

			_solutionsDDMTemplateInitializer.initialize(
				groupId, "widget-ddm-templates");

			_solutionsLayoutInitializer.initialize(groupId, fileEntriesMap);

			_solutionsLayoutSetInitializer.initialize(groupId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		Theme theme = _themeLocalService.fetchTheme(
			companyId, _SOLUTIONS_THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info(_SOLUTIONS_THEME_ID + " is not registered");
			}

			return false;
		}

		return true;
	}

	private static final String _SOLUTIONS_THEME_ID =
		"solutions_WAR_liferaysolutionstheme";

	private static final Log _log = LogFactoryUtil.getLog(
		SolutionsSiteInitializer.class);

	@Reference
	private SolutionsDDMTemplateInitializer _solutionsDDMTemplateInitializer;

	@Reference
	private SolutionsDLFileEntriesInitializer
		_solutionsDLFileEntriesInitializer;

	@Reference
	private SolutionsFragmentsInitializer _solutionsFragmentsInitializer;

	@Reference
	private SolutionsLayoutInitializer _solutionsLayoutInitializer;

	@Reference
	private SolutionsLayoutSetInitializer _solutionsLayoutSetInitializer;

	@Reference
	private SolutionsStylebookInitializer _solutionsStylebookInitializer;

	@Reference
	private ThemeLocalService _themeLocalService;

}