package org.greatage.domain.objectify;

import com.googlecode.objectify.Query;
import org.greatage.domain.internal.AbstractCriteriaVisitor;
import org.greatage.domain.Criteria;
import org.greatage.domain.Entity;
import org.greatage.domain.JunctionCriteria;
import org.greatage.domain.PropertyCriteria;
import org.greatage.domain.SortCriteria;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifyCriteriaVisitor<PK extends Serializable, E extends Entity<PK>> extends AbstractCriteriaVisitor<PK, E> {
	private final Query<? extends E> query;

	public ObjectifyCriteriaVisitor(final Query<? extends E> query) {
		this.query = query;
	}

	@Override
	protected void visitJunction(final JunctionCriteria<PK, E> criteria) {
		if (criteria.getOperator() == JunctionCriteria.Operator.OR) {
			throw new UnsupportedOperationException("OR operation is not supported by appengine queries");
		}

		for (Criteria<PK, E> child : criteria.getChildren()) {
			visit(child);
		}
	}

	@Override
	protected void visitEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " =";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitNotEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " !=";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitGreaterThanOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " >";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitGreaterOrEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " >=";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitLessThanOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " <";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitLessOrEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " <=";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitInOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " in";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitLikeOperator(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitSort(final SortCriteria<PK, E> criteria) {
		final StringBuilder condition = new StringBuilder();
		if (!criteria.isAscending()) {
			condition.append("-");
		}
		if (criteria.getPath() != null) {
			condition.append(criteria.getPath()).append('.');
		}
		condition.append(criteria.getProperty());
		query.order(condition.toString());
	}

	private String propertyName(final PropertyCriteria<PK, E> criteria) {
		return criteria.getPath() != null ?
				criteria.getPath() + "." + criteria.getProperty() :
				criteria.getProperty();
	}
}
