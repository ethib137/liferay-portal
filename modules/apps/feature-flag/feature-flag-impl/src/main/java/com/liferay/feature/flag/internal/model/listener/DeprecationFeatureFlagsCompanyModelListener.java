/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.internal.model.listener;

import com.liferay.feature.flag.company.feature.flags.CompanyFeatureFlags;
import com.liferay.feature.flag.company.feature.flags.CompanyFeatureFlagsProvider;
import com.liferay.feature.flag.configuration.DeprecationFeatureFlags;
import com.liferay.feature.flag.model.FeatureFlag;
import com.liferay.feature.flag.model.FeatureFlagType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.io.IOException;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 * @author Thiago Buarque
 */
@Component(
	configurationPid = "com.liferay.feature.flag.configuration.DeprecationFeatureFlags",
	service = ModelListener.class
)
public class DeprecationFeatureFlagsCompanyModelListener
	extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) throws ModelListenerException {
		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=",
			DeprecationFeatureFlags.class.getName(), ")");

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				Configuration configuration = configurations[0];

				Dictionary<String, Object> properties =
					configuration.getProperties();

				_disableCompanyFeatureFlags(
					company.getCompanyId(),
					Arrays.asList(
						(String[])properties.get("disabledFeatureFlagKeys")));
			}
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		try {
			String filterString = StringBundler.concat(
				"(", Constants.SERVICE_PID, "=",
				DeprecationFeatureFlags.class.getName(), ")");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if ((configurations == null) && StartupHelperUtil.isDBNew()) {
				_createDeprecatedFeatureFlagsSnapshot();
			}
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _createDeprecatedFeatureFlagsSnapshot() throws IOException {
		Configuration configuration = _configurationAdmin.getConfiguration(
			DeprecationFeatureFlags.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = new Hashtable<>();

		CompanyFeatureFlags systemFeatureFlags =
			_companyFeatureFlagsProvider.getOrCreateCompanyFeatureFlags(
				CompanyConstants.SYSTEM);

		List<FeatureFlag> deprecationFeatureFlags =
			systemFeatureFlags.getFeatureFlags(
				FeatureFlagType.DEPRECATION.getPredicate());

		String[] disabledFeatureFlagKeys =
			new String[deprecationFeatureFlags.size()];

		for (int i = 0; i < deprecationFeatureFlags.size(); i++) {
			FeatureFlag featureFlag = deprecationFeatureFlags.get(i);

			disabledFeatureFlagKeys[i] = featureFlag.getKey();
		}

		properties.put("disabledFeatureFlagKeys", disabledFeatureFlagKeys);

		configuration.update(properties);
	}

	private void _disableCompanyFeatureFlags(
		long companyId, List<String> disabledFeatureFlagKeys) {

		CompanyFeatureFlags systemFeatureFlags =
			_companyFeatureFlagsProvider.getOrCreateCompanyFeatureFlags(
				CompanyConstants.SYSTEM);

		List<FeatureFlag> deprecationFeatureFlags =
			systemFeatureFlags.getFeatureFlags(
				featureFlag -> {
					FeatureFlagType featureFlagType =
						featureFlag.getFeatureFlagType();

					return featureFlagType.equals(
						FeatureFlagType.DEPRECATION) &&
						   disabledFeatureFlagKeys.contains(featureFlag.getKey());
				});

		for (FeatureFlag featureFlag : deprecationFeatureFlags) {
			_companyFeatureFlagsProvider.setEnabled(
				companyId, featureFlag.getKey(), false);
		}
	}

	@Reference
	private CompanyFeatureFlagsProvider _companyFeatureFlagsProvider;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}