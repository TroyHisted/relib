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
package org.relib.ui;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * A tag that includes dynamic attribute support.
 *
 * @author Troy Histed
 */
public abstract class DynamicTag extends SimpleTagSupport implements DynamicAttributes {

	private final Map<String, Object> dynamicAttributes = new HashMap<String, Object>();

	/**
	 * Invokes the body of the tag, writing the results to the specified writer.
	 *
	 * <p>
	 * If the tag is self closing, or has no body, nothing will be written.
	 *
	 * @param out
	 *            the writer to write the out to
	 * @throws JspException
	 *             exception processing the jsp
	 * @throws IOException
	 *             exception writing the response
	 */
	public void doBody(Writer out) throws JspException, IOException {
		final JspFragment body = this.getJspBody();
		if (body != null) {
			this.getJspBody().invoke(out);
		}
	}

	/**
	 * Takes a map of attributes and writes them out in a name="value" format.
	 *
	 * <p>
	 * If the attribute value is null or evaluates to a blank string, the attribute will not have a value e.g.
	 * "disabled" instead of "disabled=''".
	 *
	 * @param attributes
	 *            the attributes to write out
	 * @param out
	 *            the writer to write to
	 * @throws IOException
	 *             error during write
	 */
	public void writeAttributes(Map<String, Object> attributes, final Writer out) throws IOException {
		for (final Entry<String, Object> attribute : attributes.entrySet()) {
			out.write(" " + attribute.getKey());
			if (attribute.getValue() != null && !"".equals(attribute.getValue().toString())) {
				out.write("=\"" + attribute.getValue() + "\"");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		this.dynamicAttributes.put(localName, value);
	}

	/**
	 * @return dynamic attributes
	 */
	public Map<String, Object> getDynamicAttributes() {
		return this.dynamicAttributes;
	}

}
