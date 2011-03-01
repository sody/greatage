/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.tapestry.grid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @version $Revision: 102 $ $Date: 2010-04-29 18:03:08 +0300 (Чт, 29 апр 2010) $
 */
public class TestCenteredPagesBuilder {
	private static final PaginationModel PAGINATION_MODEL = new PaginationModelImpl(1, 1, 10);

	@DataProvider(name = "buildData")
	public Object[][] getBuildData() {
		return new Object[][]{
				{5, 1, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(1), Page.page(2), Page.page(3), Page.page(4), Page.page(5),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 2, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(1), Page.page(2), Page.page(3), Page.page(4), Page.page(5),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 3, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(1), Page.page(2), Page.page(3), Page.page(4), Page.page(5),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 4, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(2), Page.page(3), Page.page(4), Page.page(5), Page.page(6),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 5, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(3), Page.page(4), Page.page(5), Page.page(6), Page.page(7),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 6, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(4), Page.page(5), Page.page(6), Page.page(7), Page.page(8),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 7, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(5), Page.page(6), Page.page(7), Page.page(8), Page.page(9),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 8, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(6), Page.page(7), Page.page(8), Page.page(9), Page.page(10),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 9, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(6), Page.page(7), Page.page(8), Page.page(9), Page.page(10),
						Page.separator(), Page.next(), Page.last()
				)},
				{5, 10, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(6), Page.page(7), Page.page(8), Page.page(9), Page.page(10),
						Page.separator(), Page.next(), Page.last()
				)},

				{4, 1, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(1), Page.page(2), Page.page(3), Page.page(4),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 2, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(1), Page.page(2), Page.page(3), Page.page(4),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 3, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(1), Page.page(2), Page.page(3), Page.page(4),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 4, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(2), Page.page(3), Page.page(4), Page.page(5),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 5, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(3), Page.page(4), Page.page(5), Page.page(6),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 6, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(4), Page.page(5), Page.page(6), Page.page(7),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 7, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(5), Page.page(6), Page.page(7), Page.page(8),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 8, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(6), Page.page(7), Page.page(8), Page.page(9),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 9, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(7), Page.page(8), Page.page(9), Page.page(10),
						Page.separator(), Page.next(), Page.last()
				)},
				{4, 10, Arrays.asList(
						Page.first(), Page.previous(), Page.separator(),
						Page.page(7), Page.page(8), Page.page(9), Page.page(10),
						Page.separator(), Page.next(), Page.last()
				)},
		};
	}

	@Test(dataProvider = "buildData")
	public void testBuild(int range, int currentPage, List<Page> expected) {
		PAGINATION_MODEL.updatePage(currentPage);
		final CenteredPagesBuilder builder = new CenteredPagesBuilder(range);
		final List<Page> actual = builder.build(PAGINATION_MODEL);
		Assert.assertEquals(actual.size(), expected.size());
		for (int i = 0; i < actual.size(); i++) {
			assertPages(actual.get(i), expected.get(i));
		}
	}

	private void assertPages(Page actual, Page expected) {
		Assert.assertEquals(actual.getType(), expected.getType());
		Assert.assertEquals(actual.getValue(PAGINATION_MODEL), expected.getValue(PAGINATION_MODEL));
		Assert.assertEquals(actual.isDisabled(PAGINATION_MODEL), expected.isDisabled(PAGINATION_MODEL));
	}
}
