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
 * Handles populating the HTTP response based on the returned information from a {@link HandleRequest}.
 *
 * @author Troy Histed
 */
interface ResponseGenerator {

	/**
	 * Populates the response based on the information provided in the value.
	 *
	 * @param request the {@link HttpServletRequest} object to populate
	 * @param response the {@link HttpServletResponse} object to populate
	 * @param value the information to populate the response with
	 */
	void generateResponse(HttpServletRequest request, HttpServletResponse response, Object value);

}
