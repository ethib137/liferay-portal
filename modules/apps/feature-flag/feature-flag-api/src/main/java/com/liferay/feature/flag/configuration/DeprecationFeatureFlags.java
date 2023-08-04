/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 * @author Thiago Buarque
 */
@ExtendedObjectClassDefinition(
	category = FeatureFlagConstants.CONFIGURATION_CATEGORY_KEY,
	generateUI = false
)
@Meta.OCD(id = "com.liferay.feature.flag.configuration.DeprecationFeatureFlags")
public interface DeprecationFeatureFlags {

	@Meta.AD(name = "disabled-deprecated-feature-flags", required = false)
	public String[] disabledFeatureFlags();

}