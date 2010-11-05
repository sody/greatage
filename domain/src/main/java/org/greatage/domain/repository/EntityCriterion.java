package org.greatage.domain.repository;

/**
 * @author Ivan Khalopik
 */
public interface EntityCriterion {

	EntityCriterion or(EntityCriterion... criterions);

	EntityCriterion and(EntityCriterion... criterions);

	EntityCriterion not();

}
