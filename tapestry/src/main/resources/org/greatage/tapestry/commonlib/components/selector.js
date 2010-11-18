/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

Tapestry.SelectorItem = Class.create({
	initialize: function(elementId, value) {
		this._element = $(elementId);
		this._value = value;
	},

	value: function() {
		return this._value;
	},

	show: function() {
		this._element.removeClassName('invisible');
	},

	hide: function() {
		this._element.addClassName('invisible');
	}
});


Tapestry.Selector = Class.create({
	initialize: function(previousId, nextId, hiddenId, selectedId, items) {
		this._previous = $(previousId);
		this._next = $(nextId);
		this._hidden = $(nextId);

		this._current = 0;
		this._items = [];
		$H(items).each(function(entry, index) {
			this._items[index] = new Tapestry.SelectorItem(entry.key, entry.value);
			if (selectedId == entry.key) {
				this._current = index;
			}
		}, this);
		this.updateControls();
		this._previous.observe("click", this.onPrevious.bindAsEventListener(this));
		this._next.observe("click", this.onNext.bindAsEventListener(this));
	},

	previous : function() {
		if (this._current > 0) {
			this._items[this._current--].hide();
			this._items[this._current].show();
			this.updateControls();
		}
	},

	next : function() {
		if (this._current < this._items.length - 1) {
			this._items[this._current++].hide();
			this._items[this._current].show();
			this.updateControls();
		}
	},

	updateControls : function() {
		if (this._current > 0) {
			this._previous.removeClassName('disabled');
		} else {
			this._previous.addClassName('disabled');
		}
		if (this._current < this._items.length - 1) {
			this._next.removeClassName('disabled');
		} else {
			this._next.addClassName('disabled');
		}
		if (this._hidden) {
			this._hidden.value = this._items[this._current].value();
		}
	},

	onPrevious : function(event) {
		this.previous();
	},

	onNext : function(event) {
		this.next();
	}

});

Tapestry.Initializer.selector = function(spec) {
	new Tapestry.Selector(spec.previousId, spec.nextId, spec.hiddenId, spec.selectedId, spec.items);
};