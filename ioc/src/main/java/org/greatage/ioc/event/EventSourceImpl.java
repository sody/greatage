/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.event;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EventSourceImpl<E extends Event> implements EventSource<E> {
	private final Class<E> eventClass;
	private final EventListener<E> listener;

	public EventSourceImpl(final Class<E> eventClass, final EventListener<E> listener) {
		this.eventClass = eventClass;
		this.listener = listener;
	}

	public Class<E> getEventClass() {
		return eventClass;
	}

	public EventListener<E> getEventListener() {
		return listener;
	}
}
