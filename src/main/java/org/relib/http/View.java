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
 * Represents the information that will be displayed to the end user.
 *
 * @author Troy Histed
 */
public class View {

	private final String viewPath;
	private final Map<String, Object> values = new HashMap<String, Object>();

	/**
	 * Constructs a new view using the specified view path.
	 *
	 * @param viewPath
	 *            the file path to the JSP
	 */
	public View(String viewPath) {
		this.viewPath = viewPath;
	}

	/**
	 * Returns a new View, constructed with the specified view path.
	 *
	 * @param viewPath
	 *            the file path to the JSP
	 * @return a new view using the specified path
	 */
	public static View of(String viewPath) {
		return new View(viewPath);
	}

	/**
	 * Adds the object to the view using the given name.
	 *
	 * @param name
	 *            the name to use when referencing the value
	 * @param value
	 *            the value to store
	 * @return this view
	 */
	public View put(String name, Object value) {
		this.values.put(name, value);
		return this;
	}

	/**
	 * @return viewPath
	 */
	public String getViewPath() {
		return this.viewPath;
	}

	/**
	 * @return the values
	 */
	public Map<String, Object> getValues() {
		return this.values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "View [viewPath=" + this.viewPath + "]";
	}

}
