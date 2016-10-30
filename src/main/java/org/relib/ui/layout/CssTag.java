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
package org.relib.ui.layout;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.relib.ui.DynamicTag;
import org.relib.util.Strings;

/**
 * Html tag designed to write out the standard html tags.
 *
 * @author Troy Histed
 */
public class CssTag extends DynamicTag {

	private String src;
	private String context;

	/**
	 * Writes out a link tag for a css file.
	 *
	 * <p>
	 * Supports dynamic attributes.
	 *
	 * <p>
	 * Uses the application context by default if the context value is not specified.
	 */
	@Override
	@SuppressWarnings("resource")
	public void doTag() throws JspException, IOException {
		final JspWriter out = this.getJspContext().getOut();

		if (Strings.isBlank(this.context)) {
			final ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
			this.context = servletContext.getContextPath();
		}

		this.getDynamicAttributes().put("rel", "stylesheet");
		this.getDynamicAttributes().put("href", this.context + this.src);

		out.print("<link");
		this.writeAttributes(this.getDynamicAttributes(), out);
		out.print(">");
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return this.src;
	}

	/**
	 * @param src
	 *            the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return this.context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "CssTag [src=" + this.src + ", context=" + this.context + "]";
	}

}
