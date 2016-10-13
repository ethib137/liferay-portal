'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './InstallDataSource.soy';

import './EvanSelect.es';

class InstallDataSource extends Component {
	created() {
		this.typesMap_ = {
			0: "custom",
			1: "liferay",
			2: "salesforce",
			3: "hubspot"
		};
	}

	onCancel_() {
		this.type_ = -1;
	}

	onAddDataSource_(data) {
		const dataSources = this.dataSources_;

		dataSources.push(
			{
				availableFields: "",
				companyId: "20116",
				createDate: 1476279728827,
				login: data.login,
				mappingDataSourceId: "32103",
				modifiedDate: 1476279728827,
				name: data.name,
				password: data.password,
				type: data.type,
				url: data.url,
				userId: "0",
				userName: ""
			}
		);

		this.dataSources_ = dataSources;

		this.type_ = -1;
	}

	onTypeSelect_(type, i) {
		this.setState({
			type_: i
		});

		console.log('this.type_:', this.type_, i);
	}

	installDataSourceClick_() {
		this.tab_ = 'installDataSource';
	}

	viewProfileClick_() {
		this.tab_ = 'profile';
	}

	syncTypesMap_(newVal) {
		this.types_ = Object.keys(newVal).map(key => newVal[key]);
	}
}

InstallDataSource.STATE = {
	dataSources_: {
		validator: core.isArray,
		value: [
			{
				availableFields: "",
				companyId: "20116",
				createDate: 1476279728827,
				login: "test@liferay.com",
				mappingDataSourceId: "32103",
				modifiedDate: 1476279728827,
				name: "First",
				password: "test",
				type: "1",
				url: "http://localhost:8080/api/jsonws/SCVUser.scvuserjsonws",
				userId: "0",
				userName: ""
			},
			{
				availableFields: "",
				companyId: "20116",
				createDate: 1476279728827,
				login: "test@liferay.com",
				mappingDataSourceId: "32103",
				modifiedDate: 1476279728827,
				name: "Second",
				password: "test",
				type: "1",
				url: "http://localhost:8080/api/jsonws/SCVUser.scvuserjsonws",
				userId: "0",
				userName: ""
			},
			{
				availableFields: "",
				companyId: "20116",
				createDate: 1476279728827,
				login: "test@liferay.com",
				mappingDataSourceId: "32103",
				modifiedDate: 1476279728827,
				name: "Third",
				password: "test",
				type: "1",
				url: "http://localhost:8080/api/jsonws/SCVUser.scvuserjsonws",
				userId: "0",
				userName: ""
			},
			{
				availableFields: "",
				companyId: "20116",
				createDate: 1476279728827,
				login: "test@liferay.com",
				mappingDataSourceId: "32103",
				modifiedDate: 1476279728827,
				name: "Fourth",
				password: "test",
				type: "1",
				url: "http://localhost:8080/api/jsonws/SCVUser.scvuserjsonws",
				userId: "0",
				userName: ""
			}
		]
	},

	tab_: {
		validator: core.isString
	},

	type_: {
		validator: core.isNumber,
		value: -1
	},

	types_: {
		validator: core.isArray,
		value: []
	},

	typesMap_: {
		validator: core.isObj,
		value: {}
	}
};

Soy.register(InstallDataSource, templates);

export default InstallDataSource;
