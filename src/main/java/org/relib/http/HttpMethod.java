/**
 * Copyright 2015 Troy Histed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.relib.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the various HTTP method types.
 *
 * <p>
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
 *
 * @author Troy Histed
 */
public enum HttpMethod {

	/**
	 * OPTIONS
	 */
	OPTIONS,

	/**
	 * HEAD
	 */
	HEAD,

	/**
	 * GET
	 */
	GET,

	/**
	 * POST
	 */
	POST,

	/**
	 * PUT
	 */
	PUT,

	/**
	 * DELETE
	 */
	DELETE,

	/**
	 * CONNECT
	 */
	CONNECT,

	/**
	 * Unknown method type
	 */
	UNKNOWN;

	static Map<String, HttpMethod> METHOD_LOOKUP = new HashMap<String, HttpMethod>();
	static {
		for (final HttpMethod method : HttpMethod.values()) {
			METHOD_LOOKUP.put(method.name(), method);
		}
	}

	/**
	 * Returns the Method that has the specified name.
	 *
	 * @param name
	 *            the method name (in all uppercase)
	 * @return the Method or UNKNOWN
	 */
	public static HttpMethod byName(String name) {
		final HttpMethod method = HttpMethod.METHOD_LOOKUP.get(name);
		return method != null ? method : HttpMethod.UNKNOWN;
	}
}
