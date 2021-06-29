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

import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
public abstract class BaseFragmentsInitializer
	implements FileDependenciesInitializer {

	public void initialize(long groupId) throws Exception {
		ServiceContext serviceContext = siteInitializerHelper.getServiceContext(
			groupId);

		_addFragmentEntries(serviceContext);
	}

	@Reference
	protected FragmentsImporter fragmentsImporter;

	@Reference
	protected SiteInitializerHelper siteInitializerHelper;

	private void _addFragmentEntries(ServiceContext serviceContext)
		throws Exception {

		Bundle bundle = getBundle();

		URL url = bundle.getEntry("/fragments.zip");

		File file = FileUtil.createTempFile(url.openStream());

		fragmentsImporter.importFragmentEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			file, false);
	}

}