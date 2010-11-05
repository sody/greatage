package org.example;

import org.greatage.domain.GenericEntityImpl;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class Example extends GenericEntityImpl {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
