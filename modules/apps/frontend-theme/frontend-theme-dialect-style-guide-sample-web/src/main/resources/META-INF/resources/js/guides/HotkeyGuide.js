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

import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const HOTKEY_TYPES = [
	{
		categoryTitle: Liferay.Language.get('hotkeys'),
		hotkeys: [
			'c-kbd',
			'c-kbd c-kbd-dark',
			'c-kbd c-kbd-light',
			'c-kbd c-kbd-monospaced',
		],
		size: 'small',
	},
	{
		categoryTitle: Liferay.Language.get('hotkey-sizes'),
		hotkeys: [
			'c-kbd c-kbd-sm',
			'c-kbd',
			'c-kbd c-kbd-lg',
			'c-kbd c-kbd-dark c-kbd-sm',
			'c-kbd c-kbd-dark',
			'c-kbd c-kbd-dark c-kbd-lg',
			'c-kbd c-kbd-light c-kbd-sm',
			'c-kbd c-kbd-light',
			'c-kbd c-kbd-light c-kbd-lg',
			'c-kbd c-kbd-monospaced c-kbd-sm',
			'c-kbd c-kbd-monospaced',
			'c-kbd c-kbd-monospaced c-kbd-lg',
		],
		size: 'medium',
	},
];

const HOTKEY_GROUPS = [
	'c-kbd c-kbd-group-sm',
	'c-kbd c-kbd-group',
	'c-kbd c-kbd-group-lg',
];

const HotkeyGuide = () => {
	return (
		<>
			{HOTKEY_TYPES.map((hotkeyType, hotkeyTypeId) => (
				<TokenGroup
					group="hotkeys"
					key={`${hotkeyTypeId}`}
					title={hotkeyType.categoryTitle}
				>
					{hotkeyType.hotkeys.map((item, itemIndex) => (
						<TokenItem
							key={`${itemIndex}`}
							label={item}
							size={hotkeyType.size}
						>
							<kbd className={item}>A</kbd>
						</TokenItem>
					))}
				</TokenGroup>
			))}

			<TokenGroup
				group="hotkey-groups"
				title={Liferay.Language.get('hotkey-groups')}
			>
				{HOTKEY_GROUPS.map((item, hotkeyId) => (
					<TokenItem key={`${hotkeyId}`} label={item} size="medium">
						<kbd className={item}>
							<kbd className="c-kbd">A</kbd>

							<span className="c-kbd-separator">+</span>

							<kbd className="c-kbd">⇧</kbd>

							<span className="c-kbd-separator">+</span>

							<kbd className="c-kbd">M</kbd>
						</kbd>
					</TokenItem>
				))}
			</TokenGroup>
		</>
	);
};

export default HotkeyGuide;
