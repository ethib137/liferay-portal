/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const OBJECT_ENTRY_FOLDER_CLASS_NAME =
	'com.liferay.object.model.ObjectEntryFolder';

const CMS_FILES_URL = `${location.origin}/o/search/v1.0/search?emptySearch=true&nestedFields=embedded,file.thumbnailURL&currentURL=/web/cms/files&filter=cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, 3)`;

const CMS_FILES_FOLDER_URL =

export const CMS_FILES_CONFIGURATION = {
	apiURL: CMS_FILES_URL,
	locator: {
		id: 'embedded.id',
		label: 'embedded.title',
		value: 'embedded.id',
	},
	type: Liferay.Language.get('document'),
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
						onClick: () => alert('clicked'),
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
						fieldName: 'title',
						label: Liferay.Language.get('title'),
						sortable: false,
					},
					{
						fieldName: 'description',
						label: Liferay.Language.get('description'),
						sortable: false,
					},
					{
						fieldName: 'fileName',
						label: Liferay.Language.get('fileName'),
						sortable: false,
					},
					{
						fieldName: 'fileExtension',
						label: Liferay.Language.get('type'),
						sortable: false,
					},
				],
			},
			thumbnail: 'table',
		},
	],
};