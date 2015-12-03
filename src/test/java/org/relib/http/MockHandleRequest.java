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

import java.lang.annotation.Annotation;

/**
 * Mock {@link HandleRequest} class.
 *
 * @author Troy Histed
 */
public class MockHandleRequest implements HandleRequest {

	private String value;
	private HttpMethod method;
	private MediaType contentType;
	private MediaType accept;

	public Class<? extends Annotation> annotationType() {
		return HandleRequest.class;
	}

	/**
	 * @return the value
	 */
	public String value() {
		return this.value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the method
	 */
	public HttpMethod method() {
		return this.method;
	}

	/**
	 * @return the method
	 */
	public HttpMethod getMethod() {
		return this.method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	/**
	 * @return the contentType
	 */
	public MediaType contentType() {
		return this.contentType;
	}

	/**
	 * @return the contentType
	 */
	public MediaType getContentType() {
		return this.contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the accept
	 */
	public MediaType accept() {
		return this.accept;
	}

	/**
	 * @return the accept
	 */
	public MediaType getAccept() {
		return this.accept;
	}

	/**
	 * @param accept the accept to set
	 */
	public void setAccept(MediaType accept) {
		this.accept = accept;
	}

}
