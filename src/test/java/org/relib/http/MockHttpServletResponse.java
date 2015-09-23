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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Mock Http Servlet Response object.
 *
 * @author Troy Histed
 */
public class MockHttpServletResponse implements HttpServletResponse {

	private final Map<String, String> headers = new HashMap<String, String>();

	private String contentType;
	private Locale locale;
	private int contentLength;
	private String characterEncoding;

	public boolean containsHeader(String name) {
		return this.headers.containsKey(name);
	}

	public void setHeader(String name, String value) {
		this.headers.put(name, value);
	}

	public void addHeader(String name, String value) {
		this.headers.put(name, value);
	}

	/******** Unimplemented *******/

	public ServletOutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public PrintWriter getWriter() throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void setCharacterEncoding(String charset) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void setBufferSize(int size) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public int getBufferSize() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void flushBuffer() throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void resetBuffer() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public boolean isCommitted() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void reset() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void addCookie(Cookie cookie) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public String encodeURL(String url) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public String encodeRedirectURL(String url) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public String encodeUrl(String url) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public String encodeRedirectUrl(String url) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void sendError(int sc, String msg) throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void sendError(int sc) throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void sendRedirect(String location) throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void setDateHeader(String name, long date) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void addDateHeader(String name, long date) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void setIntHeader(String name, int value) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void addIntHeader(String name, int value) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void setStatus(int sc) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void setStatus(int sc, String sm) {
		throw new UnsupportedOperationException("unimplemented");
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return this.locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the contentLength
	 */
	public int getContentLength() {
		return this.contentLength;
	}

	/**
	 * @param contentLength the contentLength to set
	 */
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * @return the characterEncoding
	 */
	public String getCharacterEncoding() {
		return this.characterEncoding;
	}

}
