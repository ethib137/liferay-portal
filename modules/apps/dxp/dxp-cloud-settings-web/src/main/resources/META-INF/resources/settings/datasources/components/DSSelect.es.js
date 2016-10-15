'use strict';

import templates from './DSSelect.soy';
import core from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import './SelectItem.es';

class DSSelect extends Component {
	onClick_() {
		this.open_ = !this.open_;
	}

	onSelect_(i) {
		this.open_ = false;

		this.onSelect(this.items[i], i);
	}
}

DSSelect.STATE = {
	items: {
		validator: core.isArray
	},

	label: {
		validator: core.isString
	},

	open_: {
		validator: core.isBool,
		value: false
	},

	onSelect: {
		validator: core.isFunc
	}
};

Soy.register(DSSelect, templates);

export default DSSelect;
