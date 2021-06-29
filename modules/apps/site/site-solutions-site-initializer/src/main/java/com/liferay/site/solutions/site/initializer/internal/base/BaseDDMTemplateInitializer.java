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

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.net.URL;

import java.util.Enumeration;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
public abstract class BaseDDMTemplateInitializer
	implements FileDependenciesInitializer {

	public void initialize(long groupId, String folderName) throws Exception {
		_addDDMTemplates(groupId, folderName);
	}

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DDMTemplateLocalService ddmTemplateLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected SiteInitializerHelper siteInitializerHelper;

	private void _addDDMTemplates(long groupId, String folderName)
		throws Exception {

		ServiceContext serviceContext = siteInitializerHelper.getServiceContext(
			groupId);

		Enumeration<URL> enumeration = findEntries(
			getDependenciesPath() + "/" + folderName, "ddm_template.json",
			true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			JSONObject ddmTemplateJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(url.openStream()));

			long classNameId;
			long resourceClassNameId;
			long structureId = 0;

			String ddmStructureKey = ddmTemplateJSONObject.getString(
				"ddmStructureKey");

			if (Validator.isNotNull(ddmStructureKey)) {
				DDMStructure ddmStructure = _fetchJournalDDMStructure(
					groupId, ddmStructureKey);

				classNameId = portal.getClassNameId(DDMStructure.class);

				resourceClassNameId = portal.getClassNameId(
					JournalArticle.class);

				structureId = ddmStructure.getStructureId();
			}
			else {
				classNameId = portal.getClassNameId(
					ddmTemplateJSONObject.getString("className"));

				resourceClassNameId = portal.getClassNameId(
					PortletDisplayTemplate.class);
			}

			ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), groupId, classNameId, structureId,
				resourceClassNameId,
				ddmTemplateJSONObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(),
					ddmTemplateJSONObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL, read("ddm_template.ftl", url),
				false, false, null, null, serviceContext);
		}
	}

	private DDMStructure _fetchJournalDDMStructure(
		long groupId, String ddmStructureKey) {

		return ddmStructureLocalService.fetchStructure(
			groupId, portal.getClassNameId(JournalArticle.class),
			ddmStructureKey);
	}

}