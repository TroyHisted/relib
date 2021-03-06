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

import org.relib.http.HttpMethod;
import org.relib.http.MediaType;
import org.relib.util.Strings;

/**
 * Parses an Http request into a {@link RequestInfo} object.
 *
 * @author Troy Histed
 */
class RequestInfoBuilder {

	/**
	 * Parses an {@link HttpServletRequest} into a {@link RequestInfo} object.
	 *
	 * @param request
	 *            the http request
	 * @param response
	 *            the http response
	 * @return request info object
	 */
	RequestInfo parseRequest(HttpServletRequest request, HttpServletResponse response) {
		final RequestInfo requestInfo = new RequestInfo();

		String pathUrlString = !Strings.isBlank(request.getContextPath())
				? request.getRequestURI().substring(request.getContextPath().length()) : request.getRequestURI();

		if (pathUrlString.charAt(0) == '/') {
			pathUrlString = pathUrlString.substring(1);
		}

		requestInfo.setPathParts(pathUrlString.split("/"));
		requestInfo.setMethod(HttpMethod.byName(request.getMethod()));
		requestInfo.setContentType(this.determineMediaType(request.getContentType()));
		requestInfo.setAccept(this.determineMediaType(request.getHeader("Accept")));
		requestInfo.setRequest(request);
		requestInfo.setResponse(response);

		return requestInfo;
	}

	/**
	 * Parses the given media type string looking for a matching MediaType.
	 *
	 * @param mediaTypeString
	 *            the media type string
	 * @return the matching MediaType or UNKNOWN
	 */
	private MediaType determineMediaType(String mediaTypeString) {
		if (mediaTypeString != null) {
			for (final MediaType mediaType : MediaType.values()) {
				if (mediaTypeString.startsWith(mediaType.getTypeString())) {
					return mediaType;
				}
			}
		}
		return MediaType.UNKNOWN;
	}

}
