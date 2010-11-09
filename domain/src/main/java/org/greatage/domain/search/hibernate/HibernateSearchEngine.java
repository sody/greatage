/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.search.hibernate;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.MapFieldSelector;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.greatage.domain.Entity;
import org.greatage.domain.repository.DefaultEntityFilter;
import org.greatage.domain.repository.EntityFilter;
import org.greatage.domain.repository.hibernate.HibernateCallback;
import org.greatage.domain.repository.hibernate.HibernateExecutor;
import org.greatage.domain.search.SearchEngine;
import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.event.EventSource;
import org.hibernate.search.Search;
import org.hibernate.search.backend.Work;
import org.hibernate.search.backend.WorkType;
import org.hibernate.search.backend.impl.EventSourceTransactionContext;
import org.hibernate.search.engine.DocumentBuilderIndexedEntity;
import org.hibernate.search.engine.SearchFactoryImplementor;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents hibernate based implementation of {@link org.greatage.domain.search.SearchEngine}. Search
 * methods based on searchProcessor parameter.
 *
 * @author Ivan Khalopik
 * @see SearchProcessor
 */
public class HibernateSearchEngine implements SearchEngine {
	private final HibernateExecutor executor;
	private final SearchProcessor searchProcessor;

	public HibernateSearchEngine(final HibernateExecutor executor, final SearchProcessor searchProcessor) {
		this.executor = executor;
		this.searchProcessor = searchProcessor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void index(final Class<E> entityClass) {
		final int size = executor.execute(new HibernateCallback<Integer>() {
			public Integer doInSession(Session session) throws HibernateException, SQLException {
				final Criteria criteria = DetachedCriteria.forClass(entityClass).getExecutableCriteria(session);
				return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		final int batchSize = 100;

		for (int i = 0; i < size; i += batchSize) {
			final int first = i;
			final List<E> entities = executor.execute(new HibernateCallback<List<E>>() {
				@SuppressWarnings({"unchecked"})
				public List<E> doInSession(Session session) throws HibernateException, SQLException {
					final Criteria criteria = DetachedCriteria.forClass(entityClass).getExecutableCriteria(session);
					criteria.setFirstResult(first);
					criteria.setMaxResults(batchSize);
					return criteria.list();
				}
			});
			index(entityClass, entities);
		}
	}

	protected <PK extends Serializable, E extends Entity<PK>>
	void index(final Class<E> entityClass, final List<E> entities) {
		executor.execute(new HibernateCallback<Object>() {
			public Object doInSession(Session session) throws HibernateException, SQLException {
				final SearchFactoryImplementor searchFactory = (SearchFactoryImplementor) Search.getFullTextSession(session).getSearchFactory();
				final DocumentBuilderIndexedEntity<E> builderIndexedEntity = searchFactory.getDocumentBuilderIndexedEntity(entityClass);
				final EventSourceTransactionContext transactionContext = new EventSourceTransactionContext((EventSource) session);
				for (E entity : entities) {
					Work<E> work = new Work<E>(entity, entity.getId(), WorkType.INDEX);
					searchFactory.getWorker().performWork(work, transactionContext);
				}
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> search(Class<E> entityClass, String queryString) {
		return search(createDefaultFilter(entityClass, queryString));
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> search(final EntityFilter<PK, E> filter) {
		final BooleanQuery luceneQuery = new BooleanQuery();
		if (searchProcessor != null) {
			searchProcessor.process(luceneQuery, filter);
			if (!CollectionUtils.isEmpty(luceneQuery.clauses())) {
				return executor.execute(new HibernateCallback<List<PK>>() {
					@SuppressWarnings({"unchecked"})
					public List<PK> doInSession(Session session) throws HibernateException, SQLException {
						final ArrayList<PK> result = new ArrayList<PK>();
						final SearchFactoryImplementor searchFactory = (SearchFactoryImplementor) Search.getFullTextSession(session).getSearchFactory();
						final DocumentBuilderIndexedEntity<E> builderIndexedEntity = searchFactory.getDocumentBuilderIndexedEntity(filter.getEntityClass());

						final IndexSearcher searcher = new IndexSearcher(
								searchFactory.getReaderProvider().openReader(builderIndexedEntity.getDirectoryProviders())
						);
						try {
							final TopDocs docs = searcher.search(luceneQuery, 1000);
							final MapFieldSelector selector = new MapFieldSelector(builderIndexedEntity.getIdKeywordName());
							for (ScoreDoc doc : docs.scoreDocs) {
								final Document document = searcher.doc(doc.doc, selector);
								final PK id = (PK) DocumentBuilderIndexedEntity.getDocumentId(searchFactory, filter.getEntityClass(), document);

								result.add(id);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						return result;
					}
				});
			}
		}
		return null;
	}

	/**
	 * Creates default entity filter for entityClass and queryString.
	 *
	 * @param entityClass entity class
	 * @param queryString query string
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return default entity filter for entityClass
	 */
	protected <PK extends Serializable, E extends Entity<PK>>
	EntityFilter<PK, E> createDefaultFilter(Class<E> entityClass, String queryString) {
		return new DefaultEntityFilter<PK, E>(entityClass, queryString);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("executor", executor);
		builder.append("searchProcessor", searchProcessor);
		return builder.toString();
	}
}
