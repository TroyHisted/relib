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
package org.relib.ui.input;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.relib.ui.field.Field;
import org.relib.ui.message.Message;
import org.relib.util.Strings;

/**
 * Input tag specially designed to handle a {@link Field}.
 *
 * @author Troy Histed
 */
public class FieldTag extends InputTag {

	private Field<?> field;

	@Override
	@SuppressWarnings("resource")
	public void doTag() throws JspException, IOException {
		final JspWriter out = this.getJspContext().getOut();

		final Map<String, Object> dynamicAttributes = this.getDynamicAttributes();
		final Map<String, Object> fieldAttributes = new HashMap<String, Object>();

		final Iterator<Entry<String, Object>> dynamicAttributeIterator =
				dynamicAttributes.entrySet().iterator();
		while (dynamicAttributeIterator.hasNext()) {
			final Entry<String, Object> attribute = dynamicAttributeIterator.next();
			if (attribute.getKey().startsWith("field-")) {
				fieldAttributes.put(attribute.getKey().substring(6), attribute.getValue());
				dynamicAttributeIterator.remove();
			}
		}

		final String messageClass = this.generateMessageClass(this.field.getMessage());

		final Object fieldClass = fieldAttributes.get("class");
		if (fieldClass == null) {
			fieldAttributes.put("class", "field " + messageClass);
		} else {
			fieldAttributes.put("class", "field " + messageClass + " " + String.valueOf(fieldClass));
		}

		out.print("<div");
		for (final Entry<String, Object> attribute : fieldAttributes.entrySet()) {
			out.write(" " + attribute.getKey());
			if (attribute.getValue() != null && !"".equals(attribute.getValue().toString())) {
				out.write("=\"" + attribute.getValue() + "\"");
			}
		}
		out.println(">");
		out.print("<label>");
		if (!Strings.isBlank(this.field.getLabel())) {
			out.print(this.field.getLabel());
			out.print(":");
		}
		out.println("</label>");
		super.doTag();

		if (this.field.getMessage() != null) {
			out.print("<span class=\"message " + messageClass + "\">");
			out.println(this.field.getMessageText());
			out.print("</span>");
		}

		out.println("</div>");
	}

	/**
	 * Determines the message level class name.
	 *
	 * <p>
	 * Uses the default descriptions of "error", "warning", "info", "success", and "debug" when applicable. If
	 * the level is nonstandard the class name will be built by combining the static text "level-" with the level
	 * number (e.g. "level-42").
	 *
	 * @param message
	 * @return string representation of the message level or an empty string
	 */
	private String generateMessageClass(Message message) {
		if (message == null) {
			return "";
		}

		String levelClass;
		switch (this.field.getMessage().getLevel()) {
		case Message.ERROR_LEVEL:
			levelClass = "error";
			break;
		case Message.WARNING_LEVEL:
			levelClass = "warning";
			break;
		case Message.SUCCESS_LEVEL:
			levelClass = "success";
			break;
		case Message.INFO_LEVEL:
			levelClass = "info";
			break;
		case Message.DEBUG_LEVEL:
			levelClass = "debug";
			break;
		default:
			levelClass = "level-" + this.field.getMessage().getLevel();
			break;
		}
		return levelClass;
	}

	/**
	 * @return the field
	 */
	public Field<?> getField() {
		return this.field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field<?> field) {
		this.field = field;
		this.setValue(field.getValue());
		this.setOptions(field.getOptions());
	}

}
