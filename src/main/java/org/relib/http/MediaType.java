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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the various HTTP media types.
 *
 * <p>
 * Actually represents the combination of media type and subtype. This is a very small subset of
 * http://www.iana.org/assignments/media-types/media-types.xhtml
 *
 * @author Troy Histed
 */
public enum MediaType {

	/**
	 * application/json
	 */
	JSON("application", "json"),

	/**
	 * application/pdf
	 */
	PDF("application", "pdf"),

	/**
	 * text/html
	 */
	HTML("text", "html"),

	/**
	 * text/xml
	 */
	XML("text", "xml"),

	/**
	 * text/javascript
	 */
	JAVASCRIPT("text", "javascript"),

	/**
	 * text/css
	 */
	CSS("text", "css"),

	/**
	 * Unknown
	 */
	UNKNOWN("", "");

	String type;
	String subType;
	String typeString;

	static Map<String, MediaType> MEDIA_TYPE_LOOKUP = new HashMap<String, MediaType>();
	static {
		for (final MediaType mediaType : MediaType.values()) {
			MEDIA_TYPE_LOOKUP.put(mediaType.getTypeString(), mediaType);
		}
	}

	/**
	 * Constructor.
	 *
	 * @param type
	 *            the type string
	 */
	MediaType(String type, String subType) {
		this.type = type;
		this.subType = subType;
		this.typeString = type + "/" + subType;
	}

	/**
	 * Returns the MediaType that has the specified typeString.
	 *
	 * @param typeString
	 *            the type string to look up
	 * @return the MediaType or UNKNOWN
	 */
	public static MediaType byTypeString(String typeString) {
		final MediaType mediaType = MediaType.MEDIA_TYPE_LOOKUP.get(typeString);
		return mediaType != null ? mediaType : MediaType.UNKNOWN;
	}

	/**
	 * Returns the full media type string.
	 *
	 * <p>
	 * e.g. application/json
	 *
	 * @return the type string
	 */
	public String getTypeString() {
		return this.typeString;
	}
}
