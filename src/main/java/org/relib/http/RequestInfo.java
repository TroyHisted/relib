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

import java.util.Arrays;

/**
 * Represents various bits of an Http request.
 *
 * @author Troy Histed
 */
class RequestInfo {

	private MediaType accept;
	private MediaType contentType;
	private HttpMethod method;
	private String[] pathParts;

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
	 * @return the method
	 */
	public HttpMethod getMethod() {
		return this.method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	/**
	 * @return the pathParts
	 */
	public String[] getPathParts() {
		return this.pathParts;
	}

	/**
	 * @param pathParts
	 *            the pathParts to set
	 */
	public void setPathParts(String[] pathParts) {
		this.pathParts = pathParts;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestInfo [accept=" + this.accept + ", contentType=" + this.contentType + ", method="
				+ this.method + ", pathParts=" + Arrays.toString(this.pathParts) + "]";
	}

}
