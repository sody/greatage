/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.event;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EventListener<E extends Event> {

	void handleEvent(E event);

}
