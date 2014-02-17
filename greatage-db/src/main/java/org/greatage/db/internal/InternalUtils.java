/*
 * Copyright (c) 2008-2014 Ivan Khalopik.
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

package org.greatage.db.internal;

import org.greatage.common.EncodeUtils;
import org.greatage.common.StringUtils;

/**
 * @author Ivan Khalopik
 */
public class InternalUtils {
    private static final String DEFAULT_ALGORITHM = "MD5";

    public static String calculateCheckSum(final String... parts) {
        final StringBuilder text = new StringBuilder();
        for (String part : parts) {
            text.append(part).append('\n');
        }
        final byte[] encoded = EncodeUtils.encode(text.toString().getBytes(), DEFAULT_ALGORITHM);
        return DEFAULT_ALGORITHM + ":" + StringUtils.toHexString(encoded) + ";";
    }
}
