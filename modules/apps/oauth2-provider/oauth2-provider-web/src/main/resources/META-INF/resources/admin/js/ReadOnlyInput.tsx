/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {FieldBase} from 'frontend-js-components-web';
import React, {useState} from 'react';

interface IReadOnlyInputProps extends React.HTMLAttributes<HTMLElement> {
	id: string;
	initialValue: string;
	label: string;
	tooltip: string;
	type?: string;
}

const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`;

const ReadOnlyInput: React.FC<IReadOnlyInputProps> = (props) => {
	const {id, initialValue, label, tooltip, type = 'text'} = props;

	const {observer, onOpenChange, open} = useModal();
	const [value, setValue] = useState(initialValue);
	const [modalValue, setModalValue] = useState(initialValue);

	const modalId = `${id}modal`;
	const valueChanged = modalValue !== initialValue;

	return (
		<>
			<FieldBase id={id} label={label} required={true} tooltip={tooltip}>
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							id={id}
							name={id}
							readOnly
							type={type}
							value={value}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							displayType="secondary"
							onClick={() => onOpenChange(true)}
						>
							{Liferay.Language.get('edit')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</FieldBase>

			{open && (
				<ClayModal observer={observer} spritemap={spritemap}>
					<ClayModal.Header>
						{Liferay.Language.get('edit-client-id')}
					</ClayModal.Header>

					<ClayModal.Body>
						<ClayAlert
							displayType="warning"
							spritemap={spritemap}
							title={Liferay.Language.get('warning')}
						>
							{Liferay.Language.get(
								'if-changed-clients-with-the-old-client-id-will-no-longer-be-able-to-request-new-tokens-after-you-save-the-application-details'
							)}
						</ClayAlert>

						<FieldBase id={modalId} label={label} tooltip={tooltip}>
							<ClayInput.Group>
								<ClayInput.GroupItem prepend>
									<ClayInput
										id={modalId}
										name={modalId}
										onChange={({target: {value}}) =>
											setModalValue(value)
										}
										type="text"
										value={modalValue}
									/>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem append shrink>
									<ClayButton
										disabled={!valueChanged}
										displayType="secondary"
										onClick={() =>
											setModalValue(initialValue)
										}
									>
										{Liferay.Language.get('revert')}
									</ClayButton>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</FieldBase>
					</ClayModal.Body>

					<ClayModal.Footer
						first={
							valueChanged ? (
								<span>
									<ClayIcon symbol="unlock" />

									{Liferay.Language.get('changed')}
								</span>
							) : (
								<span>
									<ClayIcon symbol="lock" />

									{Liferay.Language.get('unchanged')}
								</span>
							)
						}
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={() => {
										setModalValue(initialValue);

										onOpenChange(false);
									}}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									onClick={() => {
										setValue(modalValue);

										onOpenChange(false);
									}}
								>
									{Liferay.Language.get('apply')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};

export default ReadOnlyInput;
