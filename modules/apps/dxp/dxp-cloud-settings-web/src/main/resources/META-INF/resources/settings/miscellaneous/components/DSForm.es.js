'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './DSForm.soy';

import './DSInput.es';

class DSForm extends Component {
	onCancel_() {
		const {onCancel} = this;

		if (onCancel) {
			this.onCancel();
		}
	}

	onInput_(event, name) {
		this.data_[name] = event.target.value;
	}

	onSubmit_(event) {
		const instance = this;

		const {
			login,
			name,
			password,
			url
		} = instance.data_;

		event.preventDefault();

		instance.loading_ = true;

		console.log('onSubmit', this);

		Liferay.Service(
			'/Cloud.cloud/add-data-source',
			{
				login,
				name,
				password,
				type: instance.type.toString(),
				url
			},
			function(obj) {
				instance.loading_ = false;

				instance.onSuccess(obj);
			}
		);
	}

	rendered() {
		console.log('this.type:', this.type);
	}
}

DSForm.STATE = {
	data_: {
		validator: core.isObj,
		value: {
			name: '',
			url: '',
			login: '',
			password: ''
		}
	},

	loading_: {
		validator: core.isBool,
		value: false
	},

	onCancel: {
		validator: core.isFunc
	},

	onSuccess: {
		validator: core.isFunc
	},

	type: {
		validator: core.isNumber
	}
};

Soy.register(DSForm, templates);

export default DSForm;
