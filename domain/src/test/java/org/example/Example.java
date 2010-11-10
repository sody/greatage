/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.example;

import org.greatage.domain.AbstractEntity;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class Example extends AbstractEntity<Long> {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
