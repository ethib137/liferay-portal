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

import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const tertiaryTab = (
	<div className="align-items-center d-flex p-2">
		<span className="inline-item inline-item-before">
			<ClayIcon symbol="hashtag" />
		</span>

		<div className="mr-2 text-paragraph">Tertiary</div>

		<div>
			<div>
				<h5 className="d-inline">k</h5>

				<h1 className="d-inline">999.9</h1>

				<h5 className="d-inline">k</h5>
			</div>

			<div className="font-weight-bold text-paragraph-sm text-right">
				12.5%
			</div>
		</div>
	</div>
);

const TABS_ACTIVE_BAR_COMPONENTS = [
	{
		className: 'nav-underline-active-bar-top',
		type: 'underline',
	},
	{
		className: 'nav-secondary nav-secondary-active-bar-top',
		type: 'secondary',
	},
	{
		child: tertiaryTab,
		className: 'nav-tertiary nav-tertiary-active-bar-top',
		type: 'tertiary',
	},
];

const TABS_DISABLED_COMPONENTS = [
	{
		className: 'nav-underline',
		disabled: true,
		type: 'underline',
	},
	{
		className: 'nav-secondary',
		disabled: true,
		type: 'secondary',
	},
	{
		child: tertiaryTab,
		className: 'nav-tertiary',
		disabled: true,
		type: 'tertiary',
	},
	{
		child: 'segment',
		className: 'nav-segment ',
		disabled: true,
		type: 'segment',
	},
];

const TABS_COMPONENTS = [
	{className: 'nav-underline', type: 'primary'},
	{className: 'nav-secondary', type: 'secondary'},
	{
		child: tertiaryTab,
		className: 'nav-tertiary',
		type: 'tertiary',
	},
	{className: 'nav-segment', type: 'segment'},
];

const TABS_ICONS_COMPONENTS = [
	{className: '', icon: 'after', type: 'primary'},
	{className: '', icon: 'before', type: 'primary'},
	{className: 'nav-secondary', icon: 'after', type: 'secondary'},
	{className: 'nav-secondary', icon: 'before', type: 'secondary'},
	{
		child: tertiaryTab,
		className: 'nav-tertiary',
		type: 'tertiary',
	},
	{
		child: tertiaryTab,
		className: 'nav-tertiary',
		type: 'tertiary',
	},
	{className: 'nav-segment', icon: 'after', type: 'segment'},
	{className: 'nav-segment', icon: 'before', type: 'segment'},
];

const TABS_VERTICAL_COMPONENTS = [
	{className: 'nav-underline-vertical', type: 'primary'},
	{className: 'nav-secondary nav-secondary-vertical', type: 'secondary'},
	{
		child: tertiaryTab,
		className: 'nav-tertiary nav-tertiary-vertical',
		type: 'tertiary',
	},
];

const TABS_TYPES = [
	{components: TABS_COMPONENTS, title: Liferay.Language.get('tabs')},
	{
		className: 'd-flex',
		components: TABS_VERTICAL_COMPONENTS,
		title: Liferay.Language.get('tabs-vertical'),
	},
	{
		components: TABS_ICONS_COMPONENTS,
		title: Liferay.Language.get('tabs-icons'),
	},
	{
		components: TABS_ACTIVE_BAR_COMPONENTS,
		title: Liferay.Language.get('tabs-active-bar-position'),
	},
	{
		components: TABS_DISABLED_COMPONENTS,
		title: Liferay.Language.get('tabs-disabled'),
	},
];

const TabsGuide = () => {
	const TABS = (tab) => {
		const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
		const TABS_NAME = ['Tab 1', 'Tab 2', 'Tab 3'];

		return (
			<>
				<ClayTabs className={tab.className} modern>
					{TABS_NAME.map((name, i) => (
						<ClayTabs.Item
							active={activeTabKeyValue === i}
							disabled={tab.disabled}
							innerProps={{
								'aria-controls': 'tabpanel-1',
							}}
							key={`${i}`}
							onClick={() => setActiveTabKeyValue(i)}
						>
							{tab.icon === 'before' && (
								<span className="inline-item inline-item-before">
									<ClayIcon symbol="hashtag" />
								</span>
							)}

							{tab.child ? tab.child : name}

							{tab.icon === 'after' && (
								<span className="inline-item inline-item-after">
									<ClayIcon symbol="hashtag" />
								</span>
							)}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content
					activeIndex={activeTabKeyValue}
					className="m-3"
					fade
				>
					<ClayTabs.TabPane aria-labelledby="tab-1">
						1. Proin efficitur imperdiet dolor, a iaculis orci
						lacinia eu.
					</ClayTabs.TabPane>

					<ClayTabs.TabPane aria-labelledby="tab-2">
						2. Proin efficitur imperdiet dolor, a iaculis orci
						lacinia eu.
					</ClayTabs.TabPane>

					<ClayTabs.TabPane aria-labelledby="tab-3">
						3. Proin efficitur imperdiet dolor, a iaculis orci
						lacinia eu.
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</>
		);
	};

	return (
		<>
			{TABS_TYPES.map((tabType, tabTypeIndex) => (
				<TokenGroup
					group="tabs"
					key={`${tabTypeIndex}`}
					title={tabType.title}
				>
					{tabType.components.map((tab, i) => (
						<TokenItem
							className={`my-5 ${tabType.className}}`}
							key={`${i}`}
							label={tab.className}
							size="large"
						>
							{TABS(tab)}
						</TokenItem>
					))}
				</TokenGroup>
			))}
		</>
	);
};

export default TabsGuide;
