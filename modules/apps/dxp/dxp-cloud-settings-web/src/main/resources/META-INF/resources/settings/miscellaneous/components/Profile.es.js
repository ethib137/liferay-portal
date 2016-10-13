'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './Profile.soy';

const REGEX_OBJ = /^{.+}$/;

const REGEX_ASSOC = /\$ASSOCIATED\$/;

class Profile extends Component {
	created() {
		this.data_ = {
			"$ASSOCIATED$address": [
				{
					"Zip": "58321",
					"State": "{mvccVersion=0, regionId=19019, countryId=19, regionCode=LA, name=Louisiana , active=true}",
					"Street": "",
					"Country": "{mvccVersion=0, countryId=19, name=united-states, a2=US, a3=USA, number=840, idd=001, zipRequired=true, active=true}",
					"City": "New Orleans",
					"mappingDataSourceId": "30501",
					"id": "39271",
					"tableName": "address",
					"addressId": "39271"
				}
			],
			"First Name": "Jeffrey",
			"Middle Name": "",
			"Job Title": "",
			"userId": "39254",
			"$ASSOCIATED$phone": [
				{
					"Extension": "9247",
					"Number": "471-73-8208",
					"Primary": "true",
					"phoneId": "39270",
					"mappingDataSourceId": "30501",
					"id": "39270",
					"tableName": "phone"
				}
			],
			"scvUserProfileId": 31023,
			"Screen Name": "jparkerdv",
			"Google User Id": "",
			"$ASSOCIATED$contact": [
				{
					"Facebook SN": "",
					"Twitter SN": "",
					"Is Male?": "true",
					"contactId": "39255",
					"Skype SN": "",
					"Date of Birth": "Sun Dec 27 00:00:00 GMT 1959",
					"mappingDataSourceId": "30501",
					"id": "39255",
					"tableName": "contact"
				}
			],
			"Last Name": "Parker",
			"Open ID": "",
			"Email Address": "jparkerdv@hugedomains.com"
		};

		this.data_ = this.handleTypes_(this.data_);

		this.getAttributes_(this.data_);

		console.log('this.associated_:', this.associated_);

		console.log('this.data_2:', this.data_);
	}

	getItemDetails_(obj) {
		return Object.keys(obj).map(
			key => {
				const detail = {};

				detail.label = key;
				let value = obj[key];

				if (typeof value === 'object') {
					value = value.name;
				}

				detail.value = value;

				return detail;
			}
		);
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
					const value = data[key];

					if (value !== '') {
						attributes[key] = value;
					}
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
	}
};

Soy.register(Profile, templates);

export default Profile;
