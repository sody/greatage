/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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