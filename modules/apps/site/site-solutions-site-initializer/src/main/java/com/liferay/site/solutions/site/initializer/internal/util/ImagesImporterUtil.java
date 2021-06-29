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

package com.liferay.site.solutions.site.initializer.internal.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Eudaldo Alonso
 * @author Evan Thibodeau
 */
public class ImagesImporterUtil {

	public static List<FileEntry> importFile(
			long userId, long groupId, File file, String folderName)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (Validator.isNotNull(folderName)) {
			Folder folder = DLAppLocalServiceUtil.addFolder(
				userId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				folderName, null, serviceContext);

			folderId = folder.getFolderId();
		}

		List<FileEntry> fileEntries = new ArrayList<>();

		ZipFile zipFile = new ZipFile(file);

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			byte[] bytes = null;

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				bytes = FileUtil.getBytes(inputStream);
			}

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				null, userId, groupId, folderId, fileName,
				MimeTypesUtil.getContentType(fileName), bytes, null, null,
				serviceContext);

			fileEntries.add(fileEntry);
		}

		return fileEntries;
	}

}