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

import com.liferay.site.solutions.site.initializer.internal.base.BaseLayoutsInitializer;
import com.liferay.site.solutions.site.initializer.internal.constants.SolutionsInitializerConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Evan Thibodeau
 */
@Component(immediate = true, service = SolutionsLayoutInitializer.class)
public class SolutionsLayoutInitializer extends BaseLayoutsInitializer {

	@Override
	public ClassLoader getClassLoader() {
		return SolutionsLayoutInitializer.class.getClassLoader();
	}

	@Override
	public String getDependenciesPath() {
		return SolutionsInitializerConstants.DEPENDENCIES_PATH;
	}

}