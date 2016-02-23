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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.relib.ui.field.Field;
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

		out.println("<div class='field'>");
		out.print("<label>");
		if (!Strings.isBlank(this.field.getLabel())) {
			out.print(this.field.getLabel());
			out.print(":");
		}
		out.println("</label>");
		super.doTag();

		if (this.field.getMessage() != null) {
			out.println(this.field.getMessageText());
		}

		out.println("</div>");
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
