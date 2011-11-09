/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

package org.greatage.domain.objectify;

import com.googlecode.objectify.Objectify;
import org.greatage.domain.Transaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifyExecutorImpl implements ObjectifyExecutor {
	private final Objectify objectify;

	public ObjectifyExecutorImpl(final Objectify objectify) {
		this.objectify = objectify;
	}

	public <T> T execute(final ObjectifyCallback<T> callback) {
		try {
			return callback.doInObjectify(objectify);
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	public void clear() {
	}

	public Transaction begin() {
		return new ObjectifyTransaction(objectify.getTxn());
	}
}
