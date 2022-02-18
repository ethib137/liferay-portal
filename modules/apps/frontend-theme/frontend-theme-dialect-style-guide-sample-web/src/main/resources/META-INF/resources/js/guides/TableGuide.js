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

import ClayTable from '@clayui/table';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const TABLE = [
	{
		className: 'table-divided',
		hover: false,
		responsive: false,
	},
	{
		cellClassName: 'cell-spaced',
		className: 'table-spaced',
		hover: false,
		responsive: false,
	},
	{
		className: 'table-bordered',
		hover: false,
		responsive: false,
	},
];

const DENSITY = [
	{
		className: 'default',
		hover: false,
		responsive: false,
	},
	{
		className: 'table-sm',
		hover: false,
		responsive: false,
	},
	{
		className: 'table-lg',
		hover: false,
		responsive: false,
	},
];

const UTILITY_CLASSES = [
	{
		className: 'table-hover',
		hover: true,
		responsive: false,
	},
	{
		className: 'table-responsive',
		hover: false,
		responsive: true,
	},
];

const BIG_TEXT =
	'Lorem ipsum dolor sit amet, con Lorem ipsum dolor sit amet, con Lorem ipsum dolor Lorem ipsum dolor sit amet, con Lorem ips Lorem';

const Table = ({
	cellClassName,
	cellExpanded,
	className,
	hover,
	isResponsive,
	text = false,
}) => {
	return (
		<ClayTable
			borderedColumns={false}
			className={className}
			hover={hover}
			responsive={isResponsive}
		>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						className={`table-header-cell ${cellClassName}`}
						headingCell
					>
						Teams
					</ClayTable.Cell>

					<ClayTable.Cell
						className={`table-header-cell ${cellClassName}`}
						headingCell
					>
						Region
					</ClayTable.Cell>

					<ClayTable.Cell
						className={`table-header-cell ${cellClassName}`}
						headingCell
					>
						Country
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				<ClayTable.Row>
					<ClayTable.Cell className={cellClassName} headingTitle>
						White and Red
					</ClayTable.Cell>

					<ClayTable.Cell className={cellClassName}>
						South America
					</ClayTable.Cell>

					<ClayTable.Cell className={cellClassName}>
						Brazil
					</ClayTable.Cell>
				</ClayTable.Row>

				<ClayTable.Row>
					<ClayTable.Cell className={cellClassName} headingTitle>
						White and Purple
					</ClayTable.Cell>

					<ClayTable.Cell className={cellClassName}>
						Europe
					</ClayTable.Cell>

					<ClayTable.Cell
						className={cellClassName}
						expanded={cellExpanded}
					>
						{text}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Body>
		</ClayTable>
	);
};

const TableGuide = () => {
	return (
		<>
			<TokenGroup group="tables" title={Liferay.Language.get('tables')}>
				{TABLE.map((item) => (
					<TokenItem
						key={item.className}
						label={item.className}
						size="large"
					>
						<Table
							cellClassName={item.cellClassName}
							className={item.className}
							hover={item.hover}
							isResponsive={item.responsive}
							text="Spain"
						/>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup group="density" title={Liferay.Language.get('density')}>
				{DENSITY.map((item) => (
					<TokenItem
						key={item.className}
						label={item.className}
						size="large"
					>
						<Table
							className={item.className}
							hover={item.hover}
							isResponsive={item.responsive}
							text="Spain"
						/>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="utility-classes"
				title={Liferay.Language.get('utility-classes')}
			>
				<TokenItem label="table-striped" size="large">
					<Table
						className="table-striped"
						hover={false}
						isResponsive={false}
						text="Spain"
					/>
				</TokenItem>

				{UTILITY_CLASSES.map((item) => (
					<TokenItem
						key={item.className}
						label={item.className}
						size="large"
					>
						<Table
							className={item.className}
							hover={item.hover}
							isResponsive={item.responsive}
							text={BIG_TEXT}
						/>
					</TokenItem>
				))}

				<TokenItem label="table-cell-expand" size="large">
					<Table
						cellExpanded
						hover={false}
						isResponsive={false}
						text={BIG_TEXT}
					/>
				</TokenItem>

				<TokenItem label="table-img" size="large">
					<ClayTable hover={false} responsive={false}>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell headingCell>
									Object
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Size
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Image
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							<ClayTable.Row>
								<ClayTable.Cell headingTitle>#1</ClayTable.Cell>

								<ClayTable.Cell>60x30</ClayTable.Cell>

								<ClayTable.Cell expanded>
									<img
										className="table-img"
										src="https://via.placeholder.com/60x30"
									/>
								</ClayTable.Cell>
							</ClayTable.Row>

							<ClayTable.Row>
								<ClayTable.Cell headingTitle>#2</ClayTable.Cell>

								<ClayTable.Cell>60x120</ClayTable.Cell>

								<ClayTable.Cell expanded>
									<img
										className="table-img"
										src="https://via.placeholder.com/60x120"
									/>
								</ClayTable.Cell>
							</ClayTable.Row>

							<ClayTable.Row>
								<ClayTable.Cell headingTitle>#3</ClayTable.Cell>

								<ClayTable.Cell>100x100</ClayTable.Cell>

								<ClayTable.Cell expanded>
									<img
										className="table-img"
										src="https://via.placeholder.com/100x100"
									/>
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Body>
					</ClayTable>
				</TokenItem>

				<TokenItem label="table-title table-list-title" size="large">
					<Table
						className="table-title"
						hover={false}
						isResponsive={false}
						text="Spain"
					/>
				</TokenItem>

				<TokenItem label="table-valign-bottom" size="large">
					<Table
						cellClassName="table-valign-bottom"
						className="table-valign-bottom"
						hover={false}
						isResponsive={false}
						text={BIG_TEXT}
					/>
				</TokenItem>

				<TokenItem label="table-valign-middle" size="large">
					<Table
						cellClassName="table-valign-middle"
						className="table-valign-middle"
						hover={false}
						isResponsive={false}
						text={BIG_TEXT}
					/>
				</TokenItem>

				<TokenItem label="table-valign-top" size="large">
					<Table
						cellClassName="table-valign-top"
						className="table-valign-top"
						hover={false}
						isResponsive={false}
						text={BIG_TEXT}
					/>
				</TokenItem>

				<TokenItem label="table-column-text-start" size="large">
					<Table
						cellClassName="table-column-text-start"
						className="table-column-text-start"
						hover={false}
						isResponsive={false}
						text="Spain"
					/>
				</TokenItem>

				<TokenItem label="table-column-text-center" size="large">
					<Table
						cellClassName="table-column-text-center"
						className="table-column-text-center"
						hover={false}
						isResponsive={false}
						text="Spain"
					/>
				</TokenItem>

				<TokenItem label="table-column-text-end" size="large">
					<Table
						cellClassName="table-column-text-end"
						className="table-column-text-end"
						hover={false}
						isResponsive={false}
						text="Spain"
					/>
				</TokenItem>

				<TokenItem label="table-title-link" size="large">
					<ClayTable hover={false} responsive={false}>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell expanded headingCell>
									Teams
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Region
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Country
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							<ClayTable.Row>
								<ClayTable.Cell headingTitle>
									<a src="#"> White and Red</a>
								</ClayTable.Cell>

								<ClayTable.Cell>South America</ClayTable.Cell>

								<ClayTable.Cell>Brazil</ClayTable.Cell>
							</ClayTable.Row>

							<ClayTable.Row>
								<ClayTable.Cell headingTitle>
									<a src="#"> White and Ourple</a>
								</ClayTable.Cell>

								<ClayTable.Cell>Europe</ClayTable.Cell>

								<ClayTable.Cell>Spain</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Body>
					</ClayTable>
				</TokenItem>

				<TokenItem label="table-active" size="large">
					<ClayTable hover={false} responsive={false}>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell headingCell></ClayTable.Cell>

								<ClayTable.Cell headingCell>ID</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Title
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							<ClayTable.Row>
								<ClayTable.Cell headingTitle>
									<div className="custom-checkbox custom-control">
										<label>
											<input
												className="custom-control-input"
												type="checkbox"
											/>

											<span className="custom-control-label"></span>
										</label>
									</div>
								</ClayTable.Cell>

								<ClayTable.Cell>0001</ClayTable.Cell>

								<ClayTable.Cell>Item unselected</ClayTable.Cell>
							</ClayTable.Row>

							<ClayTable.Row className="table-active">
								<ClayTable.Cell headingTitle>
									<div className="custom-checkbox custom-control">
										<label>
											<input
												checked="true"
												className="custom-control-input"
												type="checkbox"
											/>

											<span className="custom-control-label"></span>
										</label>
									</div>
								</ClayTable.Cell>

								<ClayTable.Cell>0002</ClayTable.Cell>

								<ClayTable.Cell>Item selected</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Body>
					</ClayTable>
				</TokenItem>
			</TokenGroup>
		</>
	);
};

export default TableGuide;
