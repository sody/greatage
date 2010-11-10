/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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