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

Tapestry.Trigger = Class.create({
	initialize: function(triggerId, menuId) {
		this._trigger = $(triggerId);
		this._menu = $(menuId);
		if (this._menu.visible()) {
			this._menu.hide();
		}

		this._trigger.observe("click", this.onClick.bindAsEventListener(this));
	},

	onClick : function(event) {
		this._menu.toggle();
	}

});

Tapestry.Initializer.trigger = function(triggerId, menuId) {
	new Tapestry.Trigger(triggerId, menuId);
};