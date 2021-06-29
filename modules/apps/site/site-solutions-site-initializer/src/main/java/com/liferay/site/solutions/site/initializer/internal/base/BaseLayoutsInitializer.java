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

import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.site.solutions.site.initializer.internal.SolutionsLayoutSetInitializer;
import com.liferay.site.solutions.site.initializer.internal.constants.SolutionsInitializerConstants;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
public abstract class BaseLayoutsInitializer
	implements DependenciesInitializer {

	public ClassLoader getClassLoader() {
		return SolutionsLayoutSetInitializer.class.getClassLoader();
	}

	public String getDependenciesPath() {
		return SolutionsInitializerConstants.DEPENDENCIES_PATH;
	}

	public void initialize(Long groupId, Map<String, String> fileEntriesMap)
		throws Exception {

		ServiceContext serviceContext = siteInitializerHelper.getServiceContext(
			groupId);

		_cleanLayouts(groupId, serviceContext);

		_createLayouts(fileEntriesMap, serviceContext);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected LayoutCopyHelper layoutCopyHelper;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPageTemplateEntryLocalService
		layoutPageTemplateEntryLocalService;

	@Reference
	protected LayoutPageTemplatesImporter layoutPageTemplatesImporter;

	@Reference
	protected LayoutPageTemplateStructureLocalService
		layoutPageTemplateStructureLocalService;

	@Reference
	protected SiteInitializerHelper siteInitializerHelper;

	@Reference
	protected ThemeLocalService themeLocalService;

	private Layout _addContentLayout(
			JSONObject pageJSONObject, JSONObject pageDefinitionJSONObject,
			ServiceContext serviceContext)
		throws Exception {

		String type = StringUtil.toLowerCase(pageJSONObject.getString("type"));

		Layout layout = layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			pageJSONObject.getBoolean("private"),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), pageJSONObject.getString("name")
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			type, null, false, false, new HashMap<>(), serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		_importPageDefinition(draftLayout, pageDefinitionJSONObject);

		draftLayout = _updateLayoutTypeSettings(
			draftLayout, pageDefinitionJSONObject.getJSONObject("settings"));

		layout = layoutCopyHelper.copyLayout(draftLayout, layout);

		layoutLocalService.updateStatus(
			layout.getUserId(), layout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		layoutLocalService.updateStatus(
			layout.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return layout;
	}

	private void _cleanLayouts(long groupId, ServiceContext serviceContext)
		throws Exception {

		layoutLocalService.deleteLayouts(groupId, true, serviceContext);

		layoutLocalService.deleteLayouts(groupId, false, serviceContext);
	}

	private void _createLayouts(
			Map<String, String> fileEntriesMap, ServiceContext serviceContext)
		throws Exception {

		try {
			String json = read("/layouts/layouts.json");

			JSONArray layoutsJSONArray = jsonFactory.createJSONArray(json);

			for (int i = 0; i < layoutsJSONArray.length(); i++) {
				JSONObject jsonObject = layoutsJSONArray.getJSONObject(i);

				String path = jsonObject.getString("path");

				JSONObject pageJSONObject = jsonFactory.createJSONObject(
					read(
						StringBundler.concat("/layouts/", path, "/page.json")));

				String type = StringUtil.toLowerCase(
					pageJSONObject.getString("type"));

				if (Objects.equals(LayoutConstants.TYPE_CONTENT, type)) {
					String pageDefinitionJSON = StringUtil.replace(
						read(
							StringBundler.concat(
								"/layouts/", path, "/page-definition.json")),
						"[$", "$]", fileEntriesMap);

					_addContentLayout(
						pageJSONObject,
						jsonFactory.createJSONObject(pageDefinitionJSON),
						serviceContext);
				}
			}
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage());
		}
	}

	private String _getThemeId(long companyId, String themeName) {
		List<Theme> themes = ListUtil.filter(
			themeLocalService.getThemes(companyId),
			theme -> Objects.equals(theme.getName(), themeName));

		if (ListUtil.isNotEmpty(themes)) {
			Theme theme = themes.get(0);

			return theme.getThemeId();
		}

		return null;
	}

	private void _importPageDefinition(
		Layout draftLayout, JSONObject pageDefinitionJSONObject) {

		if (!pageDefinitionJSONObject.has("pageElement")) {
			return;
		}

		JSONObject jsonObject = pageDefinitionJSONObject.getJSONObject(
			"pageElement");

		String type = jsonObject.getString("type");

		if (Validator.isNull(type) || !Objects.equals(type, "Root")) {
			return;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure;

		try {
			layoutPageTemplateStructure =
				layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						draftLayout.getGroupId(), draftLayout.getPlid(), true);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return;
		}

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		JSONArray pageElementsJSONArray = jsonObject.getJSONArray(
			"pageElements");

		for (int j = 0; j < pageElementsJSONArray.length(); j++) {
			try {
				layoutPageTemplatesImporter.importPageElement(
					draftLayout, layoutStructure,
					layoutStructure.getMainItemId(),
					pageElementsJSONArray.getString(j), j);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	private Layout _updateLayoutTypeSettings(
		Layout layout, JSONObject settingsJSONObject) {

		if (settingsJSONObject == null) {
			return layout;
		}

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		JSONObject themeSettingsJSONObject = settingsJSONObject.getJSONObject(
			"themeSettings");

		Set<Map.Entry<String, String>> entrySet = unicodeProperties.entrySet();

		entrySet.removeIf(
			entry -> {
				String key = entry.getKey();

				return key.startsWith("lfr-theme:");
			});

		if (themeSettingsJSONObject != null) {
			for (String key : themeSettingsJSONObject.keySet()) {
				unicodeProperties.put(
					key, themeSettingsJSONObject.getString(key));
			}

			layout.setTypeSettingsProperties(unicodeProperties);
		}

		String themeName = settingsJSONObject.getString("themeName");

		if (Validator.isNotNull(themeName)) {
			String themeId = _getThemeId(layout.getCompanyId(), themeName);

			layout.setThemeId(themeId);
		}

		String colorSchemeName = settingsJSONObject.getString(
			"colorSchemeName");

		if (Validator.isNotNull(colorSchemeName)) {
			layout.setColorSchemeId(colorSchemeName);
		}

		String css = settingsJSONObject.getString("css");

		if (Validator.isNotNull(css)) {
			layout.setCss(css);
		}

		JSONObject masterPageJSONObject = settingsJSONObject.getJSONObject(
			"masterPage");

		if (masterPageJSONObject != null) {
			String key = masterPageJSONObject.getString("key");

			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(layout.getGroupId(), key);

			if (masterLayoutPageTemplateEntry == null) {
				_log.error(
					StringBundler.concat(
						"Unable to find a master page with the key, ", key,
						"lowercased with dashes replacing spaces."));
			}
			else {
				layout.setMasterLayoutPlid(
					masterLayoutPageTemplateEntry.getPlid());
			}
		}

		return layoutLocalService.updateLayout(layout);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseLayoutsInitializer.class);

}