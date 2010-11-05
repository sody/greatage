/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.search.hibernate;

import org.greatage.domain.Entity;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.util.Version;

/**
 * @author Ivan Khalopik
 */
public class PropertySearchProcessor extends AbstractSearchProcessor {
	private final String[] properties;
	private static final StandardAnalyzer ANALYZER = new StandardAnalyzer(Version.LUCENE_30);

	protected PropertySearchProcessor(Class<? extends Entity> supportedEntityClass, String... properties) {
		super(supportedEntityClass);
		this.properties = properties;
	}

	@Override
	protected void process(BooleanQuery query, String queryString) {
		for (String property : properties) {
			final QueryParser parser = new QueryParser(Version.LUCENE_30, property, ANALYZER);
			try {
				query.add(parser.parse(queryString), BooleanClause.Occur.SHOULD);
			} catch (ParseException e) {
				//do nothing
			}
		}
	}
}
