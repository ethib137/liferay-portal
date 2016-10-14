import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './ContactsHome.soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

class ContactsHome extends Component {
	created() {
		this.getContacts_();
	}

	getContacts_() {
		const instance = this;

		Liferay.Service(
			'/SCVUserProfileUtil.userprofileutil/get-scv-user-profiles',
			function(obj) {
				console.log(obj);

				instance.contacts_ = obj;
			}
		);
	}
}

ContactsHome.STATE = {
	contacts_: {
		validator: core.isArray
	}
}

Soy.register(ContactsHome, templates);

export default ContactsHome;