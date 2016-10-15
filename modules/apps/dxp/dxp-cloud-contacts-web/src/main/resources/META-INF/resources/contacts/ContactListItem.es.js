'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './ContactListItem.soy';

class ContactListItem extends Component {
	onClick_() {
		this.onClick(this.contact.id);
	}
}

ContactListItem.STATE = {
	contact: {
		validator: core.isObject
	},

	onClick: {
		validator: core.isFunc
	}
};

Soy.register(ContactListItem, templates);

export default ContactListItem;
