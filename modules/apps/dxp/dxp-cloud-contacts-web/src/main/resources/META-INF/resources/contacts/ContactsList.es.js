'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './ContactsList.soy';

import './ContactListItem.es';

class ContactsList extends Component {
	created() {
		if (this.contacts.length <= 0) {
			this.loadMore();
		}
	}
}

ContactsList.STATE = {
	contacts: {
		validator: core.isArray,
		value: []
	},

	loadMore: {
		validator: core.isFunc
	},

	loading: {
		validator: core.isBool,
		value: true
	},

	onContactClick: {
		validator: core.isFunc
	}
}

Soy.register(ContactsList, templates);

export default ContactsList;
