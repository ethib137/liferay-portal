'use strict';

import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './Test.soy';

class Test extends Component {
}

Soy.register(Test, templates);

export default Test;
