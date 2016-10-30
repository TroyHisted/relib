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
package org.relib.util;

/**
 * Represents a class that is capable of converting an object of a particular type into a string representation.
 *
 * <p>
 * Additional converter implementations may be registered by implementing this interface and adding the fully
 * qualified class name to the service providers file for {@link ToStringConverter}.
 *
 * <p>
 * A single converter instance may be used multiple times, therefore implementations must be thread safe.
 *
 * @author Troy Histed
 */
public interface ToStringConverter {

	/**
	 * Converts the specified object into the string equivalent.
	 *
	 * @param object
	 *            the object to convert
	 * @param config
	 *            the ToStringConfig information
	 * @return the string representation.
	 */
	String toString(Object object, ToStringConfig config);

	/**
	 * Declares that this converter is or is not capable of converting the specified object.
	 *
	 * @param object
	 *            the object to convert
	 * @return <code>true</code> if this converter supports converting the specified object
	 */
	boolean supports(Object object);

}
