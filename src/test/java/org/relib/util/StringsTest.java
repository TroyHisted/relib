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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link Strings} class.
 *
 * @author Troy Histed
 */
public class StringsTest {

	/**
	 * Verify a trim works with a null string.
	 */
	@Test
	public void testTrimNull() {
		Assert.assertNull(Strings.trim(null));
	}

	/**
	 * Verify a trim works with an empty string.
	 */
	@Test
	public void testTrimEmpty() {
		Assert.assertEquals("", Strings.trim(""));
	}

	/**
	 * Verify a trim works with a nothing to trim string.
	 */
	@Test
	public void testTrimNothing() {
		Assert.assertEquals("abc", Strings.trim("abc"));
	}

	/**
	 * Verify a trim works with a leading trims.
	 */
	@Test
	public void testTrimLeading() {
		Assert.assertEquals("abc", Strings.trim("  abc"));
	}

	/**
	 * Verify a trim works with a trailing trims.
	 */
	@Test
	public void testTrimTrailing() {
		Assert.assertEquals("abc", Strings.trim("abc  "));
	}

	/**
	 * Verify a trim works on both ends.
	 */
	@Test
	public void testTrimBothSides() {
		Assert.assertEquals("abc", Strings.trim(" abc "));
	}

	/**
	 * Verify a trim works with a null string.
	 */
	@Test
	public void testTrimNullSlash() {
		Assert.assertNull(Strings.trim(null, '/'));
	}

	/**
	 * Verify a trim works with an empty string.
	 */
	@Test
	public void testTrimEmptySlash() {
		Assert.assertEquals("", Strings.trim("", '/'));
	}

	/**
	 * Verify a trim works with a nothing to trim string.
	 */
	@Test
	public void testTrimNothingSlash() {
		Assert.assertEquals("abc", Strings.trim("abc", '/'));
	}

	/**
	 * Verify a trim works with a leading trims.
	 */
	@Test
	public void testTrimLeadingSlash() {
		Assert.assertEquals("abc", Strings.trim("//abc", '/'));
	}

	/**
	 * Verify a trim works with a trailing trims.
	 */
	@Test
	public void testTrimTrailingSlash() {
		Assert.assertEquals("abc", Strings.trim("abc//", '/'));
	}

	/**
	 * Verify a trim works with only the specified character being trimmed.
	 */
	@Test
	public void testTrimOnlySlash() {
		Assert.assertEquals("", Strings.trim("////", '/'));
	}

	/**
	 * Verify a trim works with for multiple characters.
	 */
	@Test
	public void testTrimMultipleCharacters() {
		Assert.assertEquals("abc", Strings.trim("/\\abc/\\", '/', '\\'));
	}

	/**
	 * Verify isBlank works with null.
	 */
	@Test
	public void testIsBlankNull() {
		Assert.assertTrue(Strings.isBlank(null));
	}

	/**
	 * Verify isBlank works with an empty string.
	 */
	@Test
	public void testIsBlankEmpty() {
		Assert.assertTrue(Strings.isBlank(""));
	}

	/**
	 * Verify isBlank works with a whitespace string.
	 */
	@Test
	public void testIsBlankWhitespace() {
		Assert.assertTrue(Strings.isBlank("  "));
	}

	/**
	 * Verify isBlank works with a character in the string.
	 */
	@Test
	public void testIsBlankCharacter() {
		Assert.assertFalse(Strings.isBlank(" - "));
	}

	/**
	 * Verify isNumeric is false with null.
	 */
	@Test
	public void testIsNumericNull() {
		Assert.assertFalse(Strings.isNumeric(null));
	}

	/**
	 * Verify isNumeric is true with an empty string.
	 */
	@Test
	public void testIsNumericEmpty() {
		Assert.assertTrue(Strings.isNumeric(""));
	}

	/**
	 * Verify isNumeric is false with whitespace.
	 */
	@Test
	public void testIsNumericWhitespace() {
		Assert.assertFalse(Strings.isNumeric(" "));
	}

	/**
	 * Verify isNumeric works with a numeric string.
	 */
	@Test
	public void testIsNumericWithNumeric() {
		Assert.assertTrue(Strings.isNumeric("0987654321"));
	}

	/**
	 * Verify isNumeric fails with an alphanumeric string.
	 */
	@Test
	public void testIsNumericAlphanumeric() {
		Assert.assertFalse(Strings.isNumeric("09876a54321"));
	}

	/**
	 * Verify substring handles null.
	 */
	@Test
	public void testSubstringNull() {
		Assert.assertNull(Strings.subString(null, 0, 9));
	}

	/**
	 * Verify substring handles a string.
	 */
	@Test
	public void testSubstringString() {
		Assert.assertEquals("b", Strings.subString("abc", 1, 2));
	}

	/**
	 * Verify capitalize handles null.
	 */
	@Test
	public void testCapitalizeNull() {
		Assert.assertEquals(null, Strings.capitalize(null));
	}

	/**
	 * Verify capitalize handles a string.
	 */
	@Test
	public void testCapitalizeString() {
		Assert.assertEquals("Test string", Strings.capitalize("test string"));
	}

	/**
	 * Verify capitalize handles a ignores non alpha characters.
	 */
	@Test
	public void testCapitalizeNumber() {
		Assert.assertEquals("5 string", Strings.capitalize("5 string"));
	}

	/**
	 * Verify default string handles null.
	 */
	@Test
	public void testDefaultStringNull() {
		Assert.assertEquals("", Strings.defaultString(null));
	}

	/**
	 * Verify default string handles a string.
	 */
	@Test
	public void testDefaultString() {
		Assert.assertEquals("test", Strings.defaultString("test"));
	}
}
