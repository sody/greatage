/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.annotations.Transactional;
import org.greatage.domain.repository.EntityRepository;
import org.greatage.domain.services.GenericEntityServiceImpl;
import org.greatage.util.I18nUtils;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class TranslatableEntityServiceImpl<E extends TranslatableEntity, Q extends TranslatableEntityQuery<E, Q>>
		extends GenericEntityServiceImpl<E, Q>
		implements TranslatableEntityService<E> {

	private final TranslationService translationService;

	public TranslatableEntityServiceImpl(EntityRepository repository, TranslationService translationService, Class<E> entityClass, Class<Q> queryClass) {
		this(repository, translationService, entityClass, queryClass, null);
	}

	public TranslatableEntityServiceImpl(EntityRepository repository, TranslationService translationService, Class<E> entityClass, Class<Q> queryClass, String entityName) {
		super(repository, entityClass, queryClass, entityName);
		this.translationService = translationService;
	}

	@Override
	public E create() {
		final E entity = super.create();
		if (entity != null) {
			final Map<String, Translation> translations = translationService.getTranslations(entity, Locale.ROOT);
			entity.setTranslations(translations);
		}
		return entity;
	}

	@Override
	public E get(Long pk) {
		return get(pk, I18nUtils.ROOT_LOCALE);
	}

	public E get(Long pk, Locale locale) {
		final E entity = super.get(pk);
		if (entity != null) {
			final Map<String, Translation> translations = translationService.getTranslations(entity, locale);
			entity.setTranslations(translations);
		}
		return entity;
	}

	@Override
	@Transactional
	public void save(E entity) {
		super.save(entity);
		translationService.saveTranslations(entity, entity.getTranslations());
	}

	@Override
	@Transactional
	public void update(E entity) {
		super.update(entity);
		translationService.saveTranslations(entity, entity.getTranslations());
	}

	@Override
	@Transactional
	public void saveOrUpdate(E entity) {
		super.saveOrUpdate(entity);
		translationService.saveTranslations(entity, entity.getTranslations());
	}

}
