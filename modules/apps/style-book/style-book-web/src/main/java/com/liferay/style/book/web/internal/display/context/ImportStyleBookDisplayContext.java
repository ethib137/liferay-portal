/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.display.context;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.style.book.importer.StyleBookEntryImporterResult;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class ImportStyleBookDisplayContext {

	public ImportStyleBookDisplayContext(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public List<String> getStyleBookEntryImporterResultNames(
		StyleBookEntryImporterResult.Status status) {

		List<StyleBookEntryImporterResult> styleBookEntryImporterResults =
			_getStyleBookEntryImporterResultNames();

		if (ListUtil.isEmpty(styleBookEntryImporterResults)) {
			return null;
		}

		return TransformUtil.transform(
			styleBookEntryImporterResults,
			styleBookEntryImporterEntry -> {
				if (styleBookEntryImporterEntry.getStatus() != status) {
					return null;
				}

				return styleBookEntryImporterEntry.getName();
			});
	}

	private List<StyleBookEntryImporterResult>
		_getStyleBookEntryImporterResultNames() {

		if (_styleBookEntryImporterResults != null) {
			return _styleBookEntryImporterResults;
		}

		_styleBookEntryImporterResults =
			(List<StyleBookEntryImporterResult>)SessionMessages.get(
				_renderRequest, "styleBookEntryImporterResults");

		return _styleBookEntryImporterResults;
	}

	private final RenderRequest _renderRequest;
	private List<StyleBookEntryImporterResult> _styleBookEntryImporterResults;

}