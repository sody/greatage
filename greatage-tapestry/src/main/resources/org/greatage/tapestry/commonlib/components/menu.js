/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

Tapestry.Menu = Class.create({
	initialize: function(triggerId, menuId) {
		this._trigger = $(triggerId);
		this._menu = $(menuId);
		this._visible = !this._trigger.hasClassName('expanded');
		this.toggle();
		this._trigger.observe("click", this.onClick.bindAsEventListener(this));
	},

	show : function() {
		if (!this.visible()) {
			this._trigger.removeClassName('collapsed');
			this._trigger.removeClassName('expanded');
			this._trigger.addClassName('expanded');
			this._menu.show();
			this._visible = true;
		}
	},

	hide : function() {
		if (this.visible()) {
			this._trigger.removeClassName('collapsed');
			this._trigger.removeClassName('expanded');
			this._trigger.addClassName('collapsed');
			this._menu.hide();
			this._visible = false;
		}
	},

	toggle : function() {
		if (this.visible()) {
			this.hide();
		} else {
			this.show();
		}
	},

	visible : function() {
		return this._visible;
	},

	onClick : function(event) {
		this.toggle();
	}

});

Tapestry.Initializer.menu = function(spec) {
	new Tapestry.Menu(spec.triggerId, spec.menuId);
};