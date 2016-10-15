import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './ContactsHome.soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

import './ContactsList.es';
import './Profile.es';

const REGEX_ASSOC = /\$ASSOCIATED\$/;

class ContactsHome extends Component {
	getContacts_() {
		const instance = this;

		instance.contactsLoading_ = true;

		Liferay.Service(
			'/SCVUserProfileUtil.userprofileutil/get-scv-user-profiles',
			{
				from: 0,
				size: 10
			},
			function(contacts) {
				console.log(contacts);

				instance.contacts_ = instance.normalizeContacts_(contacts);

				instance.contactsLoading_ = false;
			}
		);
	}

	normalizeContacts_(contacts) {
		let newContacts = [];

		contacts.forEach(
			(contact, i) => {
				let contactArr = [];

				Object.keys(contact).forEach(
					key => {
						if (!REGEX_ASSOC.test(key)) {
							const value = contact[key];

							contactArr.push(
								{
									class: _.kebabCase(key),
									label: key,
									value
								}
							);
						}
					}
				);

				// temp until data is normalized.

				if (i !== 0) {
					newContacts.push(
						{
							data: contactArr,
							id: contact.scvUserProfileId
						}
					);
				}
			}
		);

		return newContacts;
	}

	onBackClick_() {
		this.contactId_ = -1;
	}

	onContactClick_(id) {
		console.log('onContactClick_:');
		this.contactId_ = id;
	}
}

ContactsHome.STATE = {
	contactId_: {
		validator: core.isNumber,
		value: -1
	},

	contacts_: {
		validator: core.isArray,
		value: []
	},

	contactsLoading_: {
		validator: core.isBool,
		value: true
	}
}

Soy.register(ContactsHome, templates);

export default ContactsHome;