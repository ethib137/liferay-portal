'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './InstallDataSource.soy';

import './DSForm.es';
import './DSList.es';
import './DSSelect.es';
import './Profile.es';

class InstallDataSource extends Component {
	created() {
		this.getDataSources_();
		this.getDataSourceTypes_();
	}

	getDataSources_() {
		const instance = this;

		Liferay.Service(
			'/osb_scv.mappingdatasource/get-mapping-data-sources',
			function(dataSources) {
				console.log('dataSources:', dataSources);
				instance.dataSources_ = dataSources;
			}
		);
	}

	getDataSourceTypes_() {
		const instance = this;

		Liferay.Service(
			'/osb_scv.mappingdatasource/get-mapping-data-source-types',
			function(types) {
				console.log(types);

				instance.typesMap_ = types;
			}
		);
	}

	onCancel_() {
		this.type_ = -1;
	}

	onAddDataSource_(data) {
		console.log('onAddDataSource_:', data);
		const dataSources = this.dataSources_;

		dataSources.push(data);

		this.dataSources_ = dataSources;

		this.type_ = -1;
	}

	onItemDelete_(id) {
		let {dataSources_} = this;

		dataSources_ = dataSources_.filter(
			item => item.mappingDataSourceId !== id
		);

		this.dataSources_ = dataSources_;
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
		value: []
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
