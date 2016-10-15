'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './Profile.soy';

const REGEX_OBJ = /^{.+}$/;

const REGEX_ASSOC = /\$ASSOCIATED\$/;

class Profile extends Component {
	created() {
		this.getProfileData_();
	}

	getProfileData_() {
		const instance = this;

		instance.loading_ = true;

		Liferay.Service(
			'/SCVUserProfileUtil.userprofileutil/get-scv-user-profile',
			{
				scvUserProfileId: instance.id
			},
			function(data) {
				console.log(data);

				const parsedData = instance.handleTypes_(data);

				instance.getAttributes_(parsedData);

				instance.loading_ = false;
			}
		);
	}

	getItemDetails_(obj) {
		let details = [];

		Object.keys(obj).forEach(
			key => {
				let value = obj[key];

				if (typeof value === 'object') {
					value = value.name;
				}

				if (value) {
					details.push(
						{
							label: key,
							value
						}
					);
				}
			}
		);

		return details;
	}

	handleAssociated_(item) {
		const newItem = {};
		newItem.name = item.tableName;

		console.log('newItem', newItem);

		delete item.tableName;
		delete item.mappingDataSourceId;
		delete item.id;

		newItem.details = this.getItemDetails_(item);

		return newItem;
	}

	getAttributes_(data) {
		let associated = [];
		let attributes = {};

		Object.keys(data).forEach(
			key => {
				if (REGEX_ASSOC.test(key)) {
					associated.push(this.handleAssociated_(data[key][0]));
				}
				else {
					attributes[key] = data[key];
				}
			}
		);

		const obj = {};

		obj.name = 'Personal';
		obj.details = this.getItemDetails_(attributes);

		associated = [obj].concat(associated);

		this.associated_ = associated;
	}

	stringToObj_(str) {
		const obj = {};

		str = str.slice(1, str.length - 1);

		str = str.split(',');

		str.forEach(
			item => {
				item = item.trim();

				const keyValue = item.split('=');

				obj[keyValue[0]] = keyValue[1];
			}
		);

		return obj;
	}

	handleTypes_(val) {
		if (typeof val === 'string' && REGEX_OBJ.test(val)) {
			val = this.stringToObj_(val);
		}

		if (!(val instanceof Array) && typeof val === 'object') {
			val = this.parseObject_(val)
		}

		if (val instanceof Array) {
			val = this.parseArray_(val);
		}

		return val;
	}

	parseArray_(arr) {
		const newArray = arr.map(
			item => {
				return this.handleTypes_(item);
			}
		);

		return newArray;
	}

	parseObject_(obj) {
		const newObj = {};

		Object.keys(obj).map(key => {
			let val = obj[key];

			val = this.handleTypes_(val);

			newObj[key] = val;
		});

		return newObj;
	}
}

Profile.STATE = {
	associated_: {
		validator: core.isArray,
		value: []
	},

	attributes_: {
		validator: core.isArray,
		value: []
	},

	id: {
		validator: core.isNumber
	},

	loading_: {
		validator: core.isBool,
		value: true
	}
};

Soy.register(Profile, templates);

export default Profile;
