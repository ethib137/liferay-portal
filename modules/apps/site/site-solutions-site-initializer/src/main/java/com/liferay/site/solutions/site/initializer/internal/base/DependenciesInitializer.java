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

import java.io.IOException;

/**
 * @author Evan Thibodeau
 */
public interface DependenciesInitializer {

	public ClassLoader getClassLoader();

	public String getDependenciesPath();

	public default String read(String path) throws IOException {
		return StringUtil.read(getClassLoader(), getDependenciesPath() + path);
	}

}