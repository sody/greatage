package org.greatage.domain;

/**
 * This class represents generic implementation of {@link org.greatage.domain.Entity}
 *
 * @author Ivan Khalopik
 */
public class GenericEntityImpl extends AbstractEntity<Long> implements GenericEntity {
	@SuppressWarnings({"UnusedDeclaration"})
	private Long id;

	public Long getId() {
		return id;
	}
}
