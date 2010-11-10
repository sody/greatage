/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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