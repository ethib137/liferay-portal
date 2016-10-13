'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './DSItem.soy';

class DSItem extends Component {
	setItem_(item) {
		// const type = this.typesMap[item.type];

		// item.type = type;

		return item;
	}
}

DSItem.STATE = {
	item: {
		validator: core.isObj
	},

	typesMap: {
		validator: core.isObj
	}
};

Soy.register(DSItem, templates);

export default DSItem;
