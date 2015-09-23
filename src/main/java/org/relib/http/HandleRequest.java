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
 * <pre> @HandleRequest("/home")</pre>
 *
 * <p>
 * Accept
 * <pre> @HandleRequest(accept=MediaType.JSON)</pre>
 *
 * <p>
 * Content-Type
 * <pre> @HanleRequest(contentType=MediaType.JSON)</pre>
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
