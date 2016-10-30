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
package org.relib.http.request;

import org.apache.commons.beanutils.ConvertUtils;
import org.relib.http.HandleRequest;
import org.relib.http.InvalidPathParamException;
import org.relib.http.PathParam;
import org.relib.util.Strings;

/**
 * Handles creating an argument value for a request parameter based on part of the request url path.
 *
 * @author Troy Histed
 */
class ArgumentGeneratorForPathParam implements ArgumentGenerator {

	private final Class<?> type;
	private int pathIndex;

	/**
	 * Constructs an argument generator for a path param.
	 *
	 * @param pathParam
	 *            the path param annotation
	 * @param handleRequest
	 *            the handle request annotation
	 * @param type
	 *            return type to build the generator for
	 */
	public ArgumentGeneratorForPathParam(PathParam pathParam, HandleRequest handleRequest, Class<?> type) {

		this.type = type;

		final String requestPathParts[] = Strings.trim(handleRequest.value(), '/').split("/");
		for (int i = 0; i < requestPathParts.length; i++) {
			if (requestPathParts[i].equals(pathParam.value())) {
				this.pathIndex = i;
				return;
			}
		}
		throw new InvalidPathParamException(
				"The string " + pathParam.value() + " doesn't exist in the mappe url " + handleRequest.value());
	}

	/**
	 * {@inheritDoc}
	 */
	public Object generateArgument(RequestInfo requestInfo) {
		return ConvertUtils.convert(requestInfo.getPathParts()[this.pathIndex], this.type);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArgumentGeneratorForPathParam [type=" + this.type + ", pathIndex=" + this.pathIndex + "]";
	}

}
