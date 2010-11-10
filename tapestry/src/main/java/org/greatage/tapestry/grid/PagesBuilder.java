/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.grid;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public interface PagesBuilder {

	List<Page> build(PaginationModel model);

}
