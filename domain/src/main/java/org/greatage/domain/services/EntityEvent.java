package org.greatage.domain.services;

import org.greatage.domain.Entity;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 */
public class EntityEvent<PK extends Serializable, E extends Entity<PK>> extends ApplicationEvent {
	private final E entity;
	private final EventType type;
	private final EventTime time;


	/**
	 * Creates a new EntityEvent.
	 *
	 * @param service the component that published the event (never <code>null</code>)
	 * @param entity  event target
	 * @param type	type of entity operation
	 * @param time	event time (before, after)
	 */
	public EntityEvent(EntityService<PK, E> service, E entity, EventType type, EventTime time) {
		super(service);
		this.entity = entity;
		this.type = type;
		this.time = time;
	}

	public E getEntity() {
		return entity;
	}

	public EventType getType() {
		return type;
	}

	public EventTime getTime() {
		return time;
	}

	enum EventType {
		READ, WRITE, CREATE, DELETE
	}

	enum EventTime {
		BEFORE, AFTER
	}
}
