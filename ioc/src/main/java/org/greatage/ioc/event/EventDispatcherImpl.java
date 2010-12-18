/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.event;

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EventDispatcherImpl implements EventDispatcher {
	private final List<EventSource> sources;

	public EventDispatcherImpl(final List<EventSource> sources) {
		this.sources = sources;
	}

	public <E extends Event> void dispatchEvent(final E event) {
		for (EventListener<E> listener : getListeners(event)) {
			listener.handleEvent(event);
		}
	}

	@SuppressWarnings({"unchecked"})
	private <E extends Event> List<EventListener<E>> getListeners(final E event) {
		final Class<? extends Event> eventClass = event.getClass();
		final List<EventListener<E>> listeners = CollectionUtils.newList();
		for (EventSource source : sources) {
			if (eventClass.isAssignableFrom(source.getEventClass())) {
				listeners.add(source.getEventListener());
			}
		}
		return listeners;
	}
}
