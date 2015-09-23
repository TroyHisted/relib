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

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Mocking class for an http servlet request.
 *
 * @author Troy Histed
 */
public class MockHttpServletRequest implements HttpServletRequest {

	private final Map<String, String> headers = new HashMap<String, String>();
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	private int contentLength;
	private String contentType;
	private final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
	private String protocol;
	private String scheme;
	private String serverName;
	private int serverPort;
	private String remoteAddr;
	private Locale locale;
	private Enumeration locales;
	private boolean secure;
	private int remotePort;
	private String localName;
	private String localAddr;
	private int localPort;
	private String authType;
	private Cookie[] cookies;
	private String method;
	private String pathInfo;
	private String pathTranslated;
	private String contextPath;
	private String queryString;
	private String remoteUser;
	private Principal userPrincipal;
	private String requestedSessionId;
	private String requestURI;
	private StringBuffer requestURL;
	private String characterEncoding;
	private String servletPath;
	private HttpSession session;
	private boolean requestedSessionIdValid;
	private boolean requestedSessionIdFromCookie;
	private boolean requestedSessionIdFromURL;
	private boolean requestedSessionIdFromUrl;

	public String getParameter(String name) {
		final String[] values = this.parameterMap.get(name);
		return values.length > 0 ? values[0] : null;
	}

	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}

	public void setHeader(String name, String value) {
		this.headers.put(name, value);
	}

	public String getHeader(String name) {
		return this.headers.get(name);
	}

	public void setAttribute(String name, Object o) {
		this.attributes.put(name, o);
	}

	public String[] getParameterValues(String name) {
		return this.parameterMap.get(name);
	}

	/******** Unimplemented *******/

	public Enumeration getAttributeNames() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public ServletInputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public Enumeration getParameterNames() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public BufferedReader getReader() throws IOException {
		throw new UnsupportedOperationException("unimplemented");
	}

	public String getRemoteHost() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void removeAttribute(String name) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public String getRealPath(String path) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public long getDateHeader(String name) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public Enumeration getHeaders(String name) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public Enumeration getHeaderNames() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public int getIntHeader(String name) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public boolean isUserInRole(String role) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public HttpSession getSession(boolean create) {
		throw new UnsupportedOperationException("unimplemented");
	}

	/**
	 * @return the contentLength
	 */
	public int getContentLength() {
		return this.contentLength;
	}

	/**
	 * @param contentLength
	 *            the contentLength to set
	 */
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the parameterMap
	 */
	public Map getParameterMap() {
		return this.parameterMap;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return this.protocol;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return this.scheme;
	}

	/**
	 * @param scheme
	 *            the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return this.serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the getRemoteAddr
	 */
	public String getRemoteAddr() {
		return this.remoteAddr;
	}

	/**
	 * @param remoteAddr
	 *            the remoteAddr to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return this.locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the locales
	 */
	public Enumeration getLocales() {
		return this.locales;
	}

	/**
	 * @param locales
	 *            the locales to set
	 */
	public void setLocales(Enumeration locales) {
		this.locales = locales;
	}

	/**
	 * @return the secure
	 */
	public boolean isSecure() {
		return this.secure;
	}

	/**
	 * @param secure
	 *            the secure to set
	 */
	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	/**
	 * @return the remotePort
	 */
	public int getRemotePort() {
		return this.remotePort;
	}

	/**
	 * @param remotePort
	 *            the remotePort to set
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	/**
	 * @return the localName
	 */
	public String getLocalName() {
		return this.localName;
	}

	/**
	 * @param localName
	 *            the localName to set
	 */
	public void setLocalName(String localName) {
		this.localName = localName;
	}

	/**
	 * @return the localAddr
	 */
	public String getLocalAddr() {
		return this.localAddr;
	}

	/**
	 * @param localAddr
	 *            the localAddr to set
	 */
	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}

	/**
	 * @return the localPort
	 */
	public int getLocalPort() {
		return this.localPort;
	}

	/**
	 * @param localPort
	 *            the localPort to set
	 */
	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	/**
	 * @return the authType
	 */
	public String getAuthType() {
		return this.authType;
	}

	/**
	 * @param authType
	 *            the authType to set
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	/**
	 * @return the cookies
	 */
	public Cookie[] getCookies() {
		return this.cookies;
	}

	/**
	 * @param cookies
	 *            the cookies to set
	 */
	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the pathInfo
	 */
	public String getPathInfo() {
		return this.pathInfo;
	}

	/**
	 * @param pathInfo
	 *            the pathInfo to set
	 */
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	/**
	 * @return the pathTranslated
	 */
	public String getPathTranslated() {
		return this.pathTranslated;
	}

	/**
	 * @param pathTranslated
	 *            the pathTranslated to set
	 */
	public void setPathTranslated(String pathTranslated) {
		this.pathTranslated = pathTranslated;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return this.contextPath;
	}

	/**
	 * @param contextPath
	 *            the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return this.queryString;
	}

	/**
	 * @param queryString
	 *            the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * @return the remoteUser
	 */
	public String getRemoteUser() {
		return this.remoteUser;
	}

	/**
	 * @param remoteUser
	 *            the remoteUser to set
	 */
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	/**
	 * @return the userPrincipal
	 */
	public Principal getUserPrincipal() {
		return this.userPrincipal;
	}

	/**
	 * @param userPrincipal
	 *            the userPrincipal to set
	 */
	public void setUserPrincipal(Principal userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	/**
	 * @return the requestedSessionId
	 */
	public String getRequestedSessionId() {
		return this.requestedSessionId;
	}

	/**
	 * @param requestedSessionId
	 *            the requestedSessionId to set
	 */
	public void setRequestedSessionId(String requestedSessionId) {
		this.requestedSessionId = requestedSessionId;
	}

	/**
	 * @return the requestURI
	 */
	public String getRequestURI() {
		return this.requestURI;
	}

	/**
	 * @param requestURI
	 *            the requestURI to set
	 */
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	/**
	 * @return the requestURL
	 */
	public StringBuffer getRequestURL() {
		return this.requestURL;
	}

	/**
	 * @param requestURL
	 *            the requestURL to set
	 */
	public void setRequestURL(StringBuffer requestURL) {
		this.requestURL = requestURL;
	}

	/**
	 * @return the servletPath
	 */
	public String getServletPath() {
		return this.servletPath;
	}

	/**
	 * @param servletPath
	 *            the servletPath to set
	 */
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return this.session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * @return the requestedSessionIdValid
	 */
	public boolean isRequestedSessionIdValid() {
		return this.requestedSessionIdValid;
	}

	/**
	 * @param requestedSessionIdValid
	 *            the requestedSessionIdValid to set
	 */
	public void setRequestedSessionIdValid(boolean requestedSessionIdValid) {
		this.requestedSessionIdValid = requestedSessionIdValid;
	}

	/**
	 * @return the requestedSessionIdFromCookie
	 */
	public boolean isRequestedSessionIdFromCookie() {
		return this.requestedSessionIdFromCookie;
	}

	/**
	 * @param requestedSessionIdFromCookie
	 *            the requestedSessionIdFromCookie to set
	 */
	public void setRequestedSessionIdFromCookie(boolean requestedSessionIdFromCookie) {
		this.requestedSessionIdFromCookie = requestedSessionIdFromCookie;
	}

	/**
	 * @return the requestedSessionIdFromURL
	 */
	public boolean isRequestedSessionIdFromURL() {
		return this.requestedSessionIdFromURL;
	}

	/**
	 * @param requestedSessionIdFromURL
	 *            the requestedSessionIdFromURL to set
	 */
	public void setRequestedSessionIdFromURL(boolean requestedSessionIdFromURL) {
		this.requestedSessionIdFromURL = requestedSessionIdFromURL;
	}

	/**
	 * @return the requestedSessionIdFromUrl
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return this.requestedSessionIdFromUrl;
	}

	/**
	 * @param requestedSessionIdFromUrl
	 *            the requestedSessionIdFromUrl to set
	 */
	public void setRequestedSessionIdFromUrl(boolean requestedSessionIdFromUrl) {
		this.requestedSessionIdFromUrl = requestedSessionIdFromUrl;
	}

	/**
	 * @return the characterEncoding
	 */
	public String getCharacterEncoding() {
		return this.characterEncoding;
	}

	/**
	 * @param characterEncoding the characterEncoding to set
	 */
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

}