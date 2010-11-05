package org.greatage.util;

import org.greatage.resource.Messages;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class CommonMessages {
	private static final Messages COMMON_MESSAGES = I18nUtils.getMessages(CommonMessages.class);

	public static String canNotCreateNewInstance(final Class clazz) {
		return COMMON_MESSAGES.format("can-not-create-new-instance", clazz.getName());
	}

	public static String ordValuesLength() {
		return COMMON_MESSAGES.get("ord-values-length");
	}

	public static String locked() {
		return COMMON_MESSAGES.get("locked");
	}

	public static String canNotAddField(final String field, final Object clazz) {
		return COMMON_MESSAGES.format("can-not-add-field", field, clazz);
	}

	public static String canNotAddConstructor(final Object clazz) {
		return COMMON_MESSAGES.format("can-not-add-constructor", clazz);
	}

	public static String canNotAddMethod(final String method, final Object clazz) {
		return COMMON_MESSAGES.format("can-not-add-method", method, clazz);
	}

	public static String canNotConvertClass(final Object clazz) {
		return COMMON_MESSAGES.format("can-not-convert-class", clazz);
	}

	public static String nullObjectBuilder() {
		return COMMON_MESSAGES.get("null-object-builder");
	}

	public static String nullProxyClass() {
		return COMMON_MESSAGES.get("null-proxy-class");
	}

	public static String proxyClassMustHaveDefaultConstructor(final Class clazz) {
		return COMMON_MESSAGES.format("proxy-class-must-have-default-constructor", clazz);
	}

	public static String proxyClassMustBeInterface(final Class clazz) {
		return COMMON_MESSAGES.format("proxy-class-must-be-interface", clazz);
	}
}
