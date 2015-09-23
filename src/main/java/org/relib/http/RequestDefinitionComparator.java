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

import java.util.Comparator;

/**
 * Specifies the priority for request definitions.
 *
 * <p>
 * Definitions that are more specific will have greater priority than those that are less specific.
 *
 * @author Troy Histed
 */
class RequestDefinitionComparator implements Comparator<RequestDefinition> {

	/**
	 * The more specific an item is, the lower number it will have.
	 *
	 * @param thisDef the definition being compared
	 * @param otherDef the definition to compare against
	 * @return -1 if thisDef has greater priority, 1 if it has less, or 0 if they have equal priority
	 */
	public int compare(RequestDefinition thisDef, RequestDefinition otherDef) {
		int result = this.compareMethod(thisDef, otherDef);
		if (result == 0) {
			result = this.compareContentType(thisDef, otherDef);
		}
		if (result == 0) {
			result = this.compareAccept(thisDef, otherDef);
		}
		if (result == 0) {
			result = this.comparePath(thisDef, otherDef);
		}
		return result;
	}

	/**
	 * Compares the HttpMethod value.
	 *
	 * @param thisDef the definition being compared
	 * @param otherDef the definition to compare against
	 * @return -1 if thisDef has greater priority, 1 if it has less, or 0 if they have equal priority
	 */
	private int compareMethod(RequestDefinition thisDef, RequestDefinition otherDef) {
		if (thisDef.getHttpMethod() == HttpMethod.UNKNOWN) {
			return otherDef.getHttpMethod() == HttpMethod.UNKNOWN ? 0 : 1;
		}
		return otherDef.getHttpMethod() == HttpMethod.UNKNOWN ? -1 : 0;
	}

	/**
	 * Compares the ContentType value.
	 *
	 * @param thisDef the definition being compared
	 * @param otherDef the definition to compare against
	 * @return -1 if thisDef has greater priority, 1 if it has less, or 0 if they have equal priority
	 */
	private int compareContentType(RequestDefinition thisDef, RequestDefinition otherDef) {
		if (thisDef.getContentType() == MediaType.UNKNOWN) {
			return otherDef.getContentType() == MediaType.UNKNOWN ? 0 : 1;
		}
		return otherDef.getContentType() == MediaType.UNKNOWN ? -1 : 0;
	}

	/**
	 * Compares the Accept value.
	 *
	 * @param thisDef the definition being compared
	 * @param otherDef the definition to compare against
	 * @return -1 if thisDef has greater priority, 1 if it has less, or 0 if they have equal priority
	 */
	private int compareAccept(RequestDefinition thisDef, RequestDefinition otherDef) {
		if (thisDef.getAccept() == MediaType.UNKNOWN) {
			return otherDef.getAccept() == MediaType.UNKNOWN ? 0 : 1;
		}
		return otherDef.getAccept() == MediaType.UNKNOWN ? -1 : 0;
	}

	/**
	 * Compares the URL path value.
	 *
	 * <p>
	 * Longer paths are given a higher priority. If both paths are the same length then the path with the
	 * first path that's not a wild card is given priority.
	 *
	 * @param thisDef the definition being compared
	 * @param otherDef the definition to compare against
	 * @return -1 if thisDef has greater priority, 1 if it has less, or 0 if they have equal priority
	 */
	private int comparePath(RequestDefinition thisDef, RequestDefinition otherDef) {

		final PathDefinition[] thisParts = thisDef.getPathParts();
		final PathDefinition[] otherParts = otherDef.getPathParts();

		if (thisParts == null) {
			if (otherParts == null) {
				return 0;
			}
			return 1;
		} else if (otherParts == null) {
			return -1;
		}

		if (thisParts.length != otherParts.length) {
			return thisParts.length > otherParts.length ? -1 : 1;
		}

		for (int i = 0; i < thisParts.length; i++) {
			if (thisParts[i].getValue() != null) {
				if (otherParts[i].getValue() == null) {
					return -1;
				}
			} else if (otherParts[i].getValue() != null) {
				return 1;
			}
		}

		return 0;
	}

}
