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

import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Evan Thibodeau
 */
public interface FileDependenciesInitializer {

	public default Enumeration<URL> findEntries(
		String path, String filePattern, boolean recurse) {

		Bundle bundle = getBundle();

		return bundle.findEntries(path, filePattern, recurse);
	}

	public Bundle getBundle();

	public String getDependenciesPath();

	public default URL getEntry(String path) {
		Bundle bundle = getBundle();

		return bundle.getEntry(path);
	}

	public default String read(String fileName, URL url) throws Exception {
		String path = url.getPath();

		Bundle bundle = getBundle();

		URL entryURL = bundle.getEntry(
			path.substring(0, path.lastIndexOf("/") + 1) + fileName);

		return StringUtil.read(entryURL.openStream());
	}

}