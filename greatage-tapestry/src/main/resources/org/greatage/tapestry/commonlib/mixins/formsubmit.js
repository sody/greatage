/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

Tapestry.FormSubmit = Class.create({
	initialize: function(formId, clientId, event) {
		this.form = $(formId);
		this.element = $(clientId);

		this.element.observe(event, this.onTrigger.bindAsEventListener(this));
	},

	createHidden : function() {
		var hidden = new Element("input", { "type":"hidden",
			"name": this.element.id + ":hidden",
			"value": this.element.id});

		this.element.insert({after:hidden});
	},

	onTrigger : function(event) {
		Event.stop(event);
		var onsubmit = this.form.onsubmit;

		if (onsubmit == undefined || onsubmit.call(window.document, event)) {
			this.createHidden();
			this.form.submit();
		}
		return false;
	}
});

Tapestry.Initializer.formSubmit = function(formId, clientId, event) {
	new Tapestry.FormSubmit(formId, clientId, event);
};