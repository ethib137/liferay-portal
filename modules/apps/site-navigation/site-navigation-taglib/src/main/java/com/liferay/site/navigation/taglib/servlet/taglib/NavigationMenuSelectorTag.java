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

package com.liferay.site.navigation.taglib.servlet.taglib;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.site.navigation.taglib.servlet.taglib.MenuDisplayFragmentConfiguration.ContextualMenu;
import com.liferay.site.navigation.taglib.servlet.taglib.MenuDisplayFragmentConfiguration.SiteNavigationMenuSource;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Evan Thibodeau
 */
public class NavigationMenuSelectorTag extends IncludeTag {

	public String getSource() {
		return _source;
	}

	@Override
	public int processEndTag() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		JspWriter jspWriter = pageContext.getOut();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		NavigationMenuTag navigationMenuTag = _getNavigationMenuTag(
			themeDisplay.getSiteGroupId());

		String navigationMenuTagString = navigationMenuTag.doTagAsString(
			httpServletRequest, httpServletResponse);

		jspWriter.write(navigationMenuTagString);

		return EVAL_PAGE;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setSource(String source) {
		_source = source;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_source = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
	}

	private void _configureMenu(NavigationMenuTag navigationMenuTag) {
		MenuDisplayFragmentConfiguration.Source source = _getSource();

		if (source instanceof ContextualMenu) {
			ContextualMenu contextualMenu = (ContextualMenu)source;

			navigationMenuTag.setRootItemType("relative");

			if (contextualMenu == ContextualMenu.CHILDREN) {
				navigationMenuTag.setRootItemLevel(0);
			}
			else if (contextualMenu == ContextualMenu.PARENT_AND_ITS_SIBLINGS) {
				navigationMenuTag.setRootItemLevel(2);
			}
			else if (contextualMenu == ContextualMenu.SELF_AND_SIBLINGS) {
				navigationMenuTag.setRootItemLevel(1);
			}
		}
		else if (source instanceof SiteNavigationMenuSource) {
			SiteNavigationMenuSource siteNavigationMenuSource =
				(SiteNavigationMenuSource)source;

			navigationMenuTag.setNavigationMenuMode(
				NavigationMenuMode.PUBLIC_PAGES);

			if (siteNavigationMenuSource.isPrivateLayout()) {
				navigationMenuTag.setNavigationMenuMode(
					NavigationMenuMode.PRIVATE_PAGES);
			}

			navigationMenuTag.setRootItemType("select");

			long siteNavigationMenuId =
				siteNavigationMenuSource.getSiteNavigationMenuId();

			navigationMenuTag.setSiteNavigationMenuId(siteNavigationMenuId);

			long parentSiteNavigationMenuItemId =
				siteNavigationMenuSource.getParentSiteNavigationMenuItemId();

			if (parentSiteNavigationMenuItemId > 0) {
				if (_isLayoutHierarchy(siteNavigationMenuId)) {
					Layout layout = LayoutLocalServiceUtil.fetchLayout(
						parentSiteNavigationMenuItemId);

					navigationMenuTag.setRootItemId(layout.getUuid());
				}
				else {
					navigationMenuTag.setRootItemId(
						String.valueOf(parentSiteNavigationMenuItemId));
				}
			}
		}

		navigationMenuTag.setDisplayDepth(0);
	}

	private JSONObject _createJSONObject(String value) {
		try {
			return JSONFactoryUtil.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			return JSONFactoryUtil.createJSONObject();
		}
	}

	private NavigationMenuTag _getNavigationMenuTag(long groupId)
		throws Exception {

		NavigationMenuTag navigationMenuTag = new NavigationMenuTag();

		navigationMenuTag.setDdmTemplateGroupId(groupId);
		navigationMenuTag.setDdmTemplateKey("NAVBAR-BLANK-FTL");

		_configureMenu(navigationMenuTag);

		return navigationMenuTag;
	}

	private MenuDisplayFragmentConfiguration.Source _getSource() {
		String source = _source;

		if (JSONUtil.isValid(source)) {
			JSONObject jsonObject = _createJSONObject(source);

			if (jsonObject.has("contextualMenu")) {
				return ContextualMenu.parse(
					jsonObject.getString("contextualMenu"));
			}
			else if (jsonObject.has("siteNavigationMenuId")) {
				return new MenuDisplayFragmentConfiguration.
					SiteNavigationMenuSource(
						jsonObject.getLong("parentSiteNavigationMenuItemId"),
						jsonObject.getBoolean("privateLayout"),
						jsonObject.getLong("siteNavigationMenuId"));
			}
		}

		return MenuDisplayFragmentConfiguration.DEFAULT_SOURCE;
	}

	private boolean _isLayoutHierarchy(long siteNavigationMenuId) {
		if (siteNavigationMenuId == 0) {
			return true;
		}

		return false;
	}

	private static final String _PAGE = "/navigation/page.jsp";

	private String _source;

}