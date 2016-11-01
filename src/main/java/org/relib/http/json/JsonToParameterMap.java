/**
 * Copyright 2016 Troy Histed
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
package org.relib.http.json;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles parsing a json string into a map of parameters and values.
 *
 * <p>
 * Nested objects converted to properties of top level objects such that <code>{person:{name:jim,age:25}}</code>
 * will become <code>person.name=jim, person.age=25</code>.
 *
 * @author Troy Histed
 */
public class JsonToParameterMap {

	/**
	 * Converts a json string into a parameter map.
	 *
	 * @param string
	 *            the json string
	 * @return non-null map
	 */
	public static Map<String, String[]> toParameterMap(String string) {
		final char[] body = string.toCharArray();

		final Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		int depth = -1;
		final String[] keys = new String[128];
		boolean inString = false;
		boolean escaped = false;
		final int[] index = new int[128];
		final boolean[] indexed = new boolean[128];
		final StringBuilder buffer = new StringBuilder();

		for (final char character : body) {
			if (character == '"' && !escaped) {
				inString = !inString;
			} else if (inString) {
				if (!escaped && character == '\\') {
					escaped = true;
				} else {
					buffer.append(character);
					escaped = false;
				}
			} else if (character == '{') {
				depth++;
			} else if (character == '[') {
				indexed[depth] = true;
				index[depth] = 0;
			} else if (character == ']') {
				if (buffer.length() > 0) {
					parameterMap.put(generateKey(depth, keys, indexed, index),
							generateValue(inString, buffer.toString()));
					buffer.setLength(0); // clear the buffer
				}
				indexed[depth] = false;
			} else if (character == '}') {
				if (buffer.length() > 0) {
					parameterMap.put(generateKey(depth, keys, indexed, index),
							generateValue(inString, buffer.toString()));
					buffer.setLength(0); // clear the buffer
				}
				depth--;
			} else if (character == ',') {
				if (buffer.length() > 0) {
					parameterMap.put(generateKey(depth, keys, indexed, index),
							generateValue(inString, buffer.toString()));
					buffer.setLength(0); // clear the buffer
				}
				if (indexed[depth]) {
					index[depth]++;
				}
			} else if (character == ':') {
				keys[depth] = buffer.toString();
				buffer.setLength(0); // clear the buffer
			} else if (character != ' ') {
				buffer.append(character);
			}
		}

		return parameterMap;
	}

	/**
	 * Generates the actual value for a string value and return it as the first index of a String array.
	 *
	 * <p>
	 * Handles treating the string "null" as an actual null value when it's not used in a string.
	 *
	 * @param inString
	 *            <code>true</code> if the specified value was contained within a string (quotes).
	 * @param value
	 *            the value to interpret
	 * @return non-null array containing the value as the first index
	 */
	private static String[] generateValue(boolean inString, String value) {
		if (!inString && "null".equalsIgnoreCase(value)) {
			return new String[] { null };
		}
		return new String[] { value };
	}

	/**
	 * Generates the key as a string for the specified depth.
	 *
	 * @param depth
	 *            the current depth in the json object
	 * @param keys
	 *            the keys from all depths that should be used to generate the key
	 * @param indexed
	 *            array of booleans indicating which items in the keys are indexed based on their depth
	 * @param index
	 *            array holding the current index of a key at various depths
	 * @return non-null String of the key
	 */
	private static String generateKey(int depth, final String[] keys, boolean[] indexed, int[] index) {
		final StringBuilder fullKey = new StringBuilder(keys[0]);
		if (indexed[0]) {
			fullKey.append("[").append(index[0]).append("]");
		}
		for (int i = 1; i <= depth; i++) {
			fullKey.append(".").append(keys[i]);
			if (indexed[i]) {
				fullKey.append("[").append(index[depth]).append("]");
			}
		}
		return fullKey.toString();
	}

}
