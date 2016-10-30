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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates a method as a handler for a specific HTTP request pattern.
 *
 * <p>
 * Handler may designate the path (relative to the servlet mapping) using the value attribute.
 *
 * <pre>
 *  &#64;HandleRequest("/home")
 * </pre>
 *
 * <p>
 * Additionally, the handler may specify any combination of accept and contentType values. If not specified the
 * path will default to "/".
 *
 * <p>
 * Accept specifies the format of data returned by the method (the source of the http request will accept a
 * response in the specified format).
 *
 * <pre>
 *  &#64;HandleRequest(accept=MediaType.JSON)
 * </pre>
 *
 * <p>
 * Content-Type specifies the format of the data coming into the method (the contents of the request are in the
 * specified format).
 *
 * <pre>
 *  &#64;HanleRequest(contentType=MediaType.JSON)
 * </pre>
 *
 * <p>
 * The method may return any of the following:
 * <ul>
 * <li>void - no additional processing takes place
 * <li>view - the view specifies how the response will be returned.
 * </ul>
 *
 * @author Troy Histed
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandleRequest {

	/**
	 * @return the path value
	 */
	String value() default "/";

	/**
	 * @return the HTTP Method type
	 */
	HttpMethod method() default HttpMethod.UNKNOWN;

	/**
	 * @return the Accepts header value
	 */
	MediaType accept() default MediaType.UNKNOWN;

	/**
	 * @return the Content-Type value
	 */
	MediaType contentType() default MediaType.UNKNOWN;
}
