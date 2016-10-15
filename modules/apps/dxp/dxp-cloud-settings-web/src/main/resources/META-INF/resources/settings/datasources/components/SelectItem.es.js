'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './SelectItem.soy';

class SelectItem extends Component {
	onClick_() {
		this.onClick(this.i);
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
SelectItem.STATE = {
	/**
	 * @instance
	 * @type {string}
	 */
	href: {
		validator: core.isString,
		value: 'javascript:;'
	},

	/**
	 * Function called when clicking on an item.
	 * @instance
	 * @type {function}
	 */
	onClick: {
		validator: core.isFunc
	},

	/**
	 * Label of the item.
	 * @instance
	 * @type {string}
	 */
	label: {
		validator: core.isString
	},

	/**
	 * Label of the item.
	 * @instance
	 * @type {string}
	 */
	i: {
		validator: core.isNumber
	}
};

Soy.register(SelectItem, templates);

export default SelectItem;
