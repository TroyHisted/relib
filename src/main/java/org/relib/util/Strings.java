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
package org.relib.util;

/**
 * Utility method for manipulating strings.
 *
 * @author Troy Histed
 */
public class Strings {

	/**
	 * An empty string ("");
	 */
	public static final String EMPTY = "";

	/**
	 * Null safe trim.
	 *
	 * @param string the string to trim
	 * @return the trimmed string or null if the passed in string was null
	 */
	public static String trim(String string) {
		if (string != null) {
			return string.trim();
		}
		return null;
	}

	/**
	 * Null safe trim.
	 *
	 * <p>
	 * Specify the characters to trim. All characters in the string that match the specified characters
	 * will be removed from the start and end of the string.
	 *
	 * @param string the string to trim
	 * @param characters the characters to remove
	 * @return trimmed string or null if the original string was null
	 */
	public static String trim(String string, char... characters) {

		if (string == null) {
			return null;
		} else if (string.isEmpty() || characters == null || characters.length == 0) {
			return string;
		}

		final char[] stringChars = string.toCharArray();
		int startIndex = 0;
		nextCharacter:
		while (startIndex < stringChars.length) {
			for (final char character : characters) {
				if (stringChars[startIndex] == character) {
					startIndex++;
					continue nextCharacter;
				}
			}
			break;
		}

		int endIndex = stringChars.length - 1;
		previousCharacter:
		while (endIndex > startIndex) {
			for (final char character : characters) {
				if (stringChars[endIndex] == character) {
					endIndex--;
					continue previousCharacter;
				}
			}
			break;
		}

		return string.substring(startIndex, endIndex + 1);
	}

	/**
	 * Determines if a string contains any text.
	 *
	 * <p>
	 * Returns true if, after trimming whitespace, the string is empty or null.
	 *
	 * <ul>
	 * <li>Null returns true
	 * <li>EMPTY returns true
	 * <li>Whitespace returns true
	 * </ul>
	 *
	 * @param string the string to test
	 * @return <code>true</code> if the string contains any text
	 */
	public static boolean isBlank(String string) {
		return string == null || EMPTY.equals(Strings.trim(string));
	}

	/**
	 * Tests a string to determine if it contains only numeric characters.
	 *
	 * <p>
	 * An empty string is considered numeric. Decimal points are not numeric characters. Null returns false.
	 *
	 * @param string the string to test
	 * @return <code>true</code> if the specified string is numeric
	 */
	public static boolean isNumeric(String string) {
		if (string == null) {
			return false;
		}
		final char[] stringChars = string.toCharArray();
		for (final char character : stringChars) {
			if (character < '0' || character > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Null safe substring.
	 *
	 * <p>
	 * @see String#substring(int, int)
	 *
	 * @param string the string to substring
	 * @param start the starting index
	 * @param end the ending index
	 * @return either null or the substring
	 */
	public static String subString(String string, int start, int end) {
		return string == null ? null : string.substring(start, end);
	}

	/**
	 * Capitalizes the first character of the specified string.
	 *
	 * @param string the string to capitalize
	 * @return the capitalized string or null if the input was null
	 */
	public static String capitalize(String string) {
		if (string == null) {
			return null;
		}
		final char[] characters = string.toCharArray();
		characters[0] = Character.toTitleCase(characters[0]);
		return String.valueOf(characters);
	}

	/**
	 * Sanitizes the string to avoid null pointer exceptions.
	 *
	 * @param string the string to sanitize
	 * @return the string argument or Strings.EMPTY if the argument is null
	 */
	public static String defaultString(String string) {
		if (string == null) {
			return EMPTY;
		}
		return string;
	}
}
