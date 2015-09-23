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

import java.lang.reflect.Method;

/**
 * Represents a url request path that has been mapped to a specific method.
 *
 * @author Troy Histed
 */
class RequestDefinition {

	PathDefinition[] pathParts;
	HttpMethod httpMethod;
	MediaType contentType;
	MediaType accept;
	Method method;

	/**
	 * @return the pathParts
	 */
	public PathDefinition[] getPathParts() {
		return this.pathParts;
	}

	/**
	 * @param pathParts
	 *            the pathParts to set
	 */
	public void setPathParts(PathDefinition[] pathParts) {
		this.pathParts = pathParts;
	}

	/**
	 * @return the httpMethod
	 */
	public HttpMethod getHttpMethod() {
		return this.httpMethod;
	}

	/**
	 * @param httpMethod
	 *            the httpMethod to set
	 */
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * @return the contentType
	 */
	public MediaType getContentType() {
		return this.contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the accept
	 */
	public MediaType getAccept() {
		return this.accept;
	}

	/**
	 * @param accept
	 *            the accept to set
	 */
	public void setAccept(MediaType accept) {
		this.accept = accept;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return this.method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + this.httpMethod + "/" + this.contentType + "/" + this.accept + "]";
	}

}
