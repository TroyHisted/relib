/**
 * Copyright 2016 Troy Histed
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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.relib.http.MediaType;
import org.relib.http.json.Json;

/**
 * Handles building the response for MediaType.JSON.
 *
 * @author Troy Histed
 */
class ResponseGeneratorForJson implements ResponseGenerator {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("resource")
	public void generateResponse(HttpServletRequest request, HttpServletResponse response, Object value) {

		response.setContentType(MediaType.JSON.getTypeString());

		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(Json.toJson(value));
		} catch (final IOException e) {
			throw new IllegalStateException("Unable to convert object to json " + value, e);
		}

	}

}