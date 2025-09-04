/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IView} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import ItemSelectorModal, { IItemSelectorModalProps } from './itemSelectorModal';

const OBJECT_ENTRY_FOLDER_CLASS_NAME =
	'com.liferay.object.model.ObjectEntryFolder';

const CMS_FILES_URL = `${location.origin}/o/search/v1.0/search?emptySearch=true&nestedFields=embedded,file.thumbnailURL&currentURL=/web/cms/files&filter=cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, 3)`;

function CMSFilesItemSelectorModal<T extends Record<string, any>>(props: IItemSelectorModalProps<T>) {
	const {fdsProps, ...otherProps} = props;

	const [url, setURL] = useState(CMS_FILES_URL);

	return <ItemSelectorModal
		{...{
			...otherProps,
			fdsProps: {
				...fdsProps,
				apiURL: url,
				id: `itemSelectorModal-documents-${uuidv4()}`,
				views: [
					{
						contentRenderer: 'cards',
						label: Liferay.Language.get('cards'),
						name: 'cards',
						schema: {
							description: 'embedded.description',
							title: 'embedded.title',
						},
						setItemComponentProps: ({item, props}: {item: any; props: any}) => {
							const stickerProps = {
								stickerProps: {
									className: 'file-icon-color-5',
									displayType: 'unstyled',
								},
							};

							console.log('item', item);

							if (item.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME) {
								return {
									...props,
									interactive: true,
									onClick: () => {
										const folderId = item.embedded.id;

										setURL(`${location.origin}/o/search/v1.0/search?emptySearch=true&nestedFields=description,embedded,file.thumbnailURL&filter=folderId eq ${folderId}`);
									},
									onSelectChange: null,
									symbol: 'folder',
								};
							}

							if (!item.embedded.file.mimeType.startsWith('image')) {
								return {
									...props,
									imgProps: null,
									...stickerProps,
								};
							}

							return {
								...props,
								...stickerProps,
								imgProps: item.embedded.file.thumbnail
							};
						},
						thumbnail: 'cards2',
					},
					{
						contentRenderer: 'table',
						label: Liferay.Language.get('table'),
						name: 'table',
						schema: {
							fields: [
								{
									fieldName: 'embedded.title',
									label: Liferay.Language.get('title'),
									sortable: false,
								},
								{
									fieldName: 'embedded.description',
									label: Liferay.Language.get('description'),
									sortable: false,
								},
								{
									fieldName: 'embedded.file.name',
									label: Liferay.Language.get('file-name'),
									sortable: false,
								},
								{
									fieldName: 'embedded.file.mimeType',
									label: Liferay.Language.get('type'),
									sortable: false,
								},
							],
						},
						thumbnail: 'table',
					},
				] as IView[],
			},
			locator:  {
				id: 'embedded.id',
				label: 'embedded.title',
				value: 'embedded.id',
			},
			type: Liferay.Language.get('document'),
		}}
	/>;
}

export default CMSFilesItemSelectorModal;