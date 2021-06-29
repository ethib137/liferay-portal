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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.site.solutions.site.initializer.internal.util.StyleBookEntriesImporterUtil;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.File;

import java.net.URL;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
public abstract class BaseStylebookInitializer
	implements DependenciesInitializer, FileDependenciesInitializer {

	public void initialize(long groupId) throws Exception {
		ServiceContext serviceContext = siteInitializerHelper.getServiceContext(
			groupId);

		_addStyleBookEntries(serviceContext);

		_setDefaultStyleBookEntry(serviceContext);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SiteInitializerHelper siteInitializerHelper;

	@Reference
	protected StyleBookEntryLocalService styleBookEntryLocalService;

	private void _addStyleBookEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = getEntry("/style-books.zip");

		File file = FileUtil.createTempFile(url.openStream());

		StyleBookEntriesImporterUtil.importStyleBookEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), file,
			false);
	}

	private void _setDefaultStyleBookEntry(ServiceContext serviceContext) {
		try {
			String styleBooks = read("/style-books/style-books.json");

			JSONObject styleBooksJSONObject = jsonFactory.createJSONObject(
				styleBooks);

			String styleBookEntryKey = styleBooksJSONObject.getString(
				"defaultStyleBook");

			StyleBookEntry styleBookEntry =
				styleBookEntryLocalService.fetchStyleBookEntry(
					serviceContext.getScopeGroupId(), styleBookEntryKey);

			if (styleBookEntry != null) {
				styleBookEntryLocalService.updateDefaultStyleBookEntry(
					styleBookEntry.getStyleBookEntryId(), true);
			}
		}
		catch (Exception exception) {
			_log.error(exception.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStylebookInitializer.class);

}