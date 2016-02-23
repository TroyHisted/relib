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

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.relib.http.HandleRequest;
import org.relib.http.View;


/**
 * Handles building the response for a {@link View} {@link HandleRequest} return value.
 *
 * @author Troy Histed
 */
class ResponseGeneratorForView implements ResponseGenerator {

	/**
	 * {@inheritDoc}
	 */
	public void generateResponse(HttpServletRequest request, HttpServletResponse response, Object value) {

		if (!(value instanceof View)) { return; }

		final View view = (View) value;

		for (final Entry<String, Object> entry : view.getValues().entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}

		try {
			request.getRequestDispatcher(view.getViewPath()).forward(request, response);
		} catch (final ServletException e) {
			throw new IllegalStateException("Unable to generate response using : " + value, e);
		} catch (final IOException e) {
			throw new IllegalStateException("Unable to generate response using : " + value, e);
		}
	}

}
