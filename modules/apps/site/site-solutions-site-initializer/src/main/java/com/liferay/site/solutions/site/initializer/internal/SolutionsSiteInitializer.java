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
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
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
		try {}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolutionsSiteInitializer.class);

	private Bundle _bundle;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.solutions.site.initializer)"
	)
	private ServletContext _servletContext;

}