package org.greatage.util;

import java.util.List;

/**
 * This interface represents item that can be ordered according to its order identifier and constraints.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Ordered {

	/**
	 * Prefix that indicates that this item must be before item specified in suffix.
	 */
	String BEFORE = "before:";

	/**
	 * Prefix that indicates that this item must be after item specified in suffix.
	 */
	String AFTER = "after:";

	/**
	 * Gets order identifier.
	 *
	 * @return order identifier
	 */
	String getOrderId();

	/**
	 * Gets list of order constraints. They must be like <tt>after:<another order id></tt>.
	 *
	 * @return list of order constraints or empty list
	 */
	List<String> getOrderConstraints();

}
