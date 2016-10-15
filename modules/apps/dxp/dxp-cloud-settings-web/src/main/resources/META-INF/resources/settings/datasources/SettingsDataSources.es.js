import Component from 'metal-component';
import templates from './SettingsDataSources.soy';
import Router from 'metal-router/src/Router';
import Soy from 'metal-soy';
import 'metal-toast';

import '../sections/SettingsSections.soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

import './components/InstallDataSource.es';

class SettingsDataSources extends Component {
}

Soy.register(SettingsDataSources, templates);

Router.router().on('endNavigate', (event) => {
	// Reset form after submit navigate
	if (event.form) {
		event.form.reset();
	}
});

export default SettingsDataSources;