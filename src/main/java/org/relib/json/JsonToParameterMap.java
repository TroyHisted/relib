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
package org.relib.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Troy Histed
 */
public class JsonToParameterMap {

	public static Map<String, String[]> toParameterMap(String string) {
		final char[] body = string.toCharArray();

		final Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		int depth = -1;
		final String[] keys = new String[128];
		boolean inString = false;
		final boolean inArray = false;
		final List<?> tempArray = null;
		StringBuilder buffer = new StringBuilder();

		for (final char character : body) {

			if (character == '{') {
				depth++;
//			} else if (character == '[') {
//				inArray = true;
//			} else if (character == ']') {
//				inArray = false;
			} else if (character == '}') {
				if (buffer.length() > 0) {
					String value = buffer.toString();
					if (!inString && "null".equalsIgnoreCase(value)) {
						value = null;
					}
					String fullKey = keys[0];
					for (int i = 1 ; i <= depth; i++) {
						fullKey += "." + keys[i];
					}
					parameterMap.put(fullKey, new String[] { value });
					buffer = new StringBuilder();
				}
				depth--;
			} else if (character == ',') {
				if (buffer.length() > 0) {
					String value = buffer.toString();
					if (!inString && "null".equalsIgnoreCase(value)) {
						value = null;
					}
					String fullKey = keys[0];
					for (int i = 1 ; i <= depth; i++) {
						fullKey += "." + keys[i];
					}
					parameterMap.put(fullKey, new String[] { value });
					buffer = new StringBuilder();
				}
			} else if (character == '"') {
				inString = !inString;
			} else if (character == ':') {
				keys[depth] = buffer.toString();
//				System.err.println("adding " + buffer.toString() + " to keys " + depth);
				buffer = new StringBuilder();
			} else if (character != ' ' || inString) {
				buffer.append(character);
			}

		}

		return parameterMap;
	}

}
