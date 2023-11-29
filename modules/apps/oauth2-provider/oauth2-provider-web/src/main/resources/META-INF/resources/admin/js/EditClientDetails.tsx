/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ReadOnlyInput from './ReadOnlyInput';

interface IEditClientDetailsProps extends React.HTMLAttributes<HTMLElement> {
	clientId: string;
	clientSecret: string;
	portletNamespace: string;
}

const EditClientDetails: React.FC<IEditClientDetailsProps> = (props) => {
	return (
		<>
			<ReadOnlyInput
				id={`${props.portletNamespace}clientId`}
				initialValue={props.clientId}
				label={Liferay.Language.get('client-id')}
				tooltip={Liferay.Language.get('client-id-help[oauth2]')}
			/>

			<ReadOnlyInput
				id={`${props.portletNamespace}clientSecret`}
				initialValue={props.clientSecret}
				label={Liferay.Language.get('client-secret')}
				tooltip={Liferay.Language.get('client-secret-help[oauth2]')}
				type="password"
			/>
		</>
	);
};

export default EditClientDetails;
