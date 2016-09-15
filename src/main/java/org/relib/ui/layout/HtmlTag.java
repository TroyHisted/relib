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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.relib.ui.DynamicTag;

/**
 * Html tag designed to write out the standard html tags.
 *
 * @author Troy Histed
 */
public class HtmlTag extends DynamicTag {

	/**
	 * Writes out the HTML5 standard tag.
	 */
	@Override
	@SuppressWarnings("resource")
	public void doTag() throws JspException, IOException {
		final JspWriter out = this.getJspContext().getOut();

		out.println("<!doctype html>");
		if (this.getDynamicAttributes().get("lang") == null) {
			this.getDynamicAttributes().put("lang", "en");
		}
		out.print("<html");
		this.writeAttributes(this.getDynamicAttributes(), out);
		out.println(">");
		this.doBody(out);
		out.println("</html>");
	}

}
