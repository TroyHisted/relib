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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.relib.http.HandleRequest;


/**
 * Handles building the response for a void {@link HandleRequest} return value.
 *
 * <p>
 * Does nothing.
 *
 * @author Troy Histed
 */
class ResponseGeneratorForVoid implements ResponseGenerator {

	/**
	 * {@inheritDoc}
	 */
	public void generateResponse(HttpServletRequest request, HttpServletResponse response, Object value) {
		// Do nothing.
	}

}
