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

/**
 * Represents a url request path that has been mapped to a specific method.
 *
 * @author Troy Histed
 */
class PathDefinition {

	Class<?> type;
	int parameterIndex;
	String value;

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * @return the parameterIndex
	 */
	public int getParameterIndex() {
		return this.parameterIndex;
	}

	/**
	 * @param parameterIndex
	 *            the parameterIndex to set
	 */
	public void setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PathDefinition [type=" + this.type + ", parameterIndex=" + this.parameterIndex + ", value="
				+ this.value + "]";
	}

}
