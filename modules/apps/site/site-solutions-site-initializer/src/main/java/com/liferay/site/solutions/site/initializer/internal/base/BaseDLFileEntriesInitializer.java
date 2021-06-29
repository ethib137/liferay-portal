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

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.site.solutions.site.initializer.internal.util.ImagesImporterUtil;

import java.io.File;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
public abstract class BaseDLFileEntriesInitializer
	implements FileDependenciesInitializer {

	public Map<String, String> initialize(Long groupId, String folderName)
		throws Exception {

		List<FileEntry> fileEntries = _addDLFileEntries(
			folderName, siteInitializerHelper.getServiceContext(groupId));

		return _getFileEntriesMap(fileEntries);
	}

	@Reference
	protected DLURLHelper dlurlHelper;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SiteInitializerHelper siteInitializerHelper;

	private List<FileEntry> _addDLFileEntries(
			String folderName, ServiceContext serviceContext)
		throws Exception {

		URL url = getEntry("/" + folderName + ".zip");

		File file = FileUtil.createTempFile(url.openStream());

		return ImagesImporterUtil.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), file,
			folderName);
	}

	private Map<String, String> _getFileEntriesMap(List<FileEntry> fileEntries)
		throws Exception {

		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : fileEntries) {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				jsonFactory.looseSerialize(fileEntry));

			jsonObject.put("alt", StringPool.BLANK);

			fileEntriesMap.put(
				"JSON_" + fileEntry.getFileName(), jsonObject.toString());

			fileEntriesMap.put(
				"ID_" + fileEntry.getFileName(),
				String.valueOf(fileEntry.getFileEntryId()));

			fileEntriesMap.put(
				"URL_" + fileEntry.getFileName(),
				dlurlHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false));
		}

		return fileEntriesMap;
	}

}