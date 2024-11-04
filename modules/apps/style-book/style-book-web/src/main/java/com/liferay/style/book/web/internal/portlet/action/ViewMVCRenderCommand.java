/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.web.internal.display.context.StyleBookDisplayContext;
import com.liferay.style.book.web.internal.display.context.StyleBookManagementToolbarDisplayContext;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/", "mvc.command.name=/style_book/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);
		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(renderResponse);

		StyleBookDisplayContext styleBookDisplayContext =
			new StyleBookDisplayContext(
				httpServletRequest, liferayPortletRequest,
				liferayPortletResponse);

		StyleBookManagementToolbarDisplayContext
			styleBookManagementToolbarDisplayContext =
				new StyleBookManagementToolbarDisplayContext(
					httpServletRequest, liferayPortletRequest,
					liferayPortletResponse, _cetManager,
					_frontendTokenDefinitionRegistry,
					styleBookDisplayContext.
						getStyleBookEntriesSearchContainer());

		renderRequest.setAttribute(
			StyleBookManagementToolbarDisplayContext.class.getName(),
			styleBookManagementToolbarDisplayContext);
		renderRequest.setAttribute(
			StyleBookDisplayContext.class.getName(), styleBookDisplayContext);

		return "/view.jsp";
	}

	@Reference
	private CETManager _cetManager;

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

}