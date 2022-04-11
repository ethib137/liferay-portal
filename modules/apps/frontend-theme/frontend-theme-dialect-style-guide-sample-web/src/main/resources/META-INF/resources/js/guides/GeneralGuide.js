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

import classNames from 'classnames';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const BORDERS = [
	'rounded-xs',
	'rounded-sm',
	'rounded',
	'rounded-lg',
	'rounded-xl',
	'rounded-xxl',
	'rounded-circle',
	'rounded-pill',
];

const BORDER_PRIMARY = [
	'border-brand-primary-darken-5',
	'border-brand-primary-darken-4',
	'border-brand-primary-darken-3',
	'border-brand-primary-darken-2',
	'border-brand-primary-darken-1',
	'border-brand-primary',
	'border-brand-primary-lighten-1',
	'border-brand-primary-lighten-2',
	'border-brand-primary-lighten-3',
	'border-brand-primary-lighten-4',
	'border-brand-primary-lighten-5',
];

const BORDER_SECONDARY = [
	'border-brand-secondary-darken-5',
	'border-brand-secondary-darken-4',
	'border-brand-secondary-darken-3',
	'border-brand-secondary-darken-2',
	'border-brand-secondary-darken-1',
	'border-brand-secondary',
	'border-brand-secondary-lighten-1',
	'border-brand-secondary-lighten-2',
	'border-brand-secondary-lighten-3',
	'border-brand-secondary-lighten-4',
	'border-brand-secondary-lighten-5',
];

const NEUTRAL_COLORS = [
	'border-neutral-0',
	'border-neutral-1',
	'border-neutral-2',
	'border-neutral-3',
	'border-neutral-4',
	'border-neutral-5',
	'border-neutral-6',
	'border-neutral-7',
	'border-neutral-8',
	'border-neutral-9',
	'border-neutral-10',
];

const ACCENT_COLORS = [
	'border-accent-1',
	'border-accent-1-lighten',
	'border-accent-2',
	'border-accent-2-lighten',
	'border-accent-3',
	'border-accent-3-lighten',
	'border-accent-4',
	'border-accent-4-lighten',
	'border-accent-5',
	'border-accent-5-lighten',
	'border-accent-6',
	'border-accent-6-lighten',
];

const STATE_COLORS_SUCCESS = [
	'border-success-darken-2',
	'border-success-darken-1',
	'border-success',
	'border-success-lighten-1',
	'border-success-lighten-2',
];

const STATE_COLORS_INFO = [
	'border-info-darken-2',
	'border-info-darken-1',
	'border-info',
	'border-info-lighten-1',
	'border-info-lighten-2',
];

const STATE_COLORS_WARNING = [
	'border-warning-darken-2',
	'border-warning-darken-1',
	'border-warning',
	'border-warning-lighten-1',
	'border-warning-lighten-2',
];

const STATE_COLORS_DANGER = [
	'border-danger-darken-2',
	'border-danger-darken-1',
	'border-danger',
	'border-danger-lighten-1',
	'border-danger-lighten-2',
];

const RATIOS = [
	'aspect-ratio',
	'aspect-ratio-16-to-9',
	'aspect-ratio-8-to-3',
	'aspect-ratio-4-to-3',
];

const SHADOWS = ['shadow-sm', 'shadow', 'shadow-lg'];

const SPACERS = [
	'spacer-1',
	'spacer-2',
	'spacer-3',
	'spacer-4',
	'spacer-5',
	'spacer-6',
	'spacer-7',
	'spacer-8',
	'spacer-9',
	'spacer-10',
];

const GeneralGuide = () => {
	return (
		<>
			<TokenGroup group="spacers" title={Liferay.Language.get('spacers')}>
				{SPACERS.map((item) => (
					<TokenItem
						border={true}
						className={item.replace('spacer', 'pr')}
						key={item}
						label={item}
						size="large"
					/>
				))}
			</TokenGroup>

			<TokenGroup group="borders" title={Liferay.Language.get('borders')}>
				{BORDERS.map((item) => (
					<TokenItem
						border={true}
						className={item}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-primary-colors')}
			>
				{BORDER_PRIMARY.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-secondary-colors')}
			>
				{BORDER_SECONDARY.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-neutral-colors')}
			>
				{NEUTRAL_COLORS.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-accent-colors')}
			>
				{ACCENT_COLORS.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-success-colors')}
			>
				{STATE_COLORS_SUCCESS.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-info-colors')}
			>
				{STATE_COLORS_INFO.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-warning-colors')}
			>
				{STATE_COLORS_WARNING.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="borders"
				title={Liferay.Language.get('border-danger-colors')}
			>
				{STATE_COLORS_DANGER.map((item) => (
					<TokenItem
						border={true}
						className={`rounded-sm ${item}`}
						key={item}
						label={item}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="shadows"
				title={Liferay.Language.get('box-shadow')}
			>
				{SHADOWS.map((item) => (
					<TokenItem className={item} key={item} label={item} />
				))}
			</TokenGroup>
			<TokenGroup
				group="ratios"
				title={Liferay.Language.get('aspect-ratios')}
			>
				{RATIOS.map((item) => (
					<TokenItem
						border={true}
						key={item}
						label={item}
						size="medium"
					>
						<span
							className={classNames('aspect-ratio', item)}
						></span>
					</TokenItem>
				))}
			</TokenGroup>
		</>
	);
};

export default GeneralGuide;
