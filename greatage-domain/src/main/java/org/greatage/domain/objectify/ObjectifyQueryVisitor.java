package org.greatage.domain.objectify;

import com.googlecode.objectify.Query;
import org.greatage.domain.Entity;
import org.greatage.domain.Repository;
import org.greatage.domain.internal.AbstractQueryVisitor;
import org.greatage.domain.internal.JunctionCriteria;
import org.greatage.domain.internal.PropertyCriteria;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifyQueryVisitor<PK extends Serializable, E extends Entity<PK>> extends AbstractQueryVisitor<PK, E> {
	private final Query<? extends E> query;

	public ObjectifyQueryVisitor(final Query<? extends E> query) {
		this.query = query;
	}

	@Override
	protected void visitJunction(final JunctionCriteria<PK, E> criteria) {
		if (criteria.getOperator() == JunctionCriteria.Operator.OR) {
			throw new UnsupportedOperationException("OR operation is not supported by appengine queries");
		}

		for (Repository.Criteria<PK, E> child : criteria.getChildren()) {
			visitCriteria(child);
		}
	}

	@Override
	protected void visitEqual(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " =";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitNotEqual(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " !=";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitGreaterThan(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " >";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitGreaterOrEqual(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " >=";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitLessThan(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " <";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitLessOrEqual(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " <=";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitIn(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String criterion = propertyName + " in";

		query.filter(criterion, criteria.getValue());
	}

	@Override
	protected void visitLike(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

//	@Override
//	protected void visitSort(final SortCriteria<PK, E> criteria) {
//		final StringBuilder condition = new StringBuilder();
//		if (!criteria.isAscending()) {
//			condition.append("-");
//		}
//		if (criteria.getPath() != null) {
//			condition.append(criteria.getPath()).append('.');
//		}
//		condition.append(criteria.getProperty());
//		query.order(condition.toString());
//	}

	private String propertyName(final PropertyCriteria<PK, E> criteria) {
		return criteria.getPath() != null ?
				criteria.getPath() + "." + criteria.getProperty() :
				criteria.getProperty();
	}
}
