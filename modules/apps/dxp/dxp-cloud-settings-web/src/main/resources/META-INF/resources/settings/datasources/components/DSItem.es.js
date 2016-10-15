'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './DSItem.soy';

class DSItem extends Component {
	onCloseClick_() {
		const instance = this;

		const {mappingDataSourceId} = instance.item;

		if (confirm('Are you sure you want to delete this Data Source? This cannot be undone.')) {
			Liferay.Service(
				'/osb_scv.mappingdatasource/delete-mapping-data-source',
				{
					mappingDataSourceId
				},
				function(obj) {
					console.log('onCloseClick_', obj);

					instance.onDelete(mappingDataSourceId);
				}
			);
		}
	}
}

DSItem.STATE = {
	item: {
		validator: core.isObj
	},

	onDelete: {
		validator: core.isFunc
	},

	typesMap: {
		validator: core.isObj
	}
};

Soy.register(DSItem, templates);

export default DSItem;
