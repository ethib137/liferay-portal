'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './DSInput.soy';

class DSInput extends Component {
	onInput_(event) {
		const {name, onInput} = this;

		if (onInput) {
			onInput(event, name);
		}
	}
}

DSInput.STATE = {
	disabled: {
		setter: val => {
			var disabled = '';

			if (val) {
				disabled = 'disabled'
			}

			return disabled;
		},
		validator: core.isBool
	},

	label: {
		validator: core.isString
	},

	name: {
		validator: core.isString
	},

	onInput: {
		validator: core.isFunc
	},

	type: {
		validator: core.isString,
		value: 'text'
	},

	value: {
		validator: core.isString
	}

};

Soy.register(DSInput, templates);

export default DSInput;
