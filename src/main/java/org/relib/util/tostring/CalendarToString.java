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
package org.relib.util.tostring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.relib.util.ToStringConfig;
import org.relib.util.ToStringConverter;

/**
 * Handles converting a {@link Date} into a {@link String}.
 *
 * @author Troy Histed
 */
public class CalendarToString implements ToStringConverter {

	/**
	 * Formats a {@link Calendar} as just the date and time.
	 *
	 * <p>
	 * If the time is midnight, only the date will be used.
	 *
	 * {@inheritDoc}
	 */
	public String toString(Object object, ToStringConfig config) {
		final StringBuilder buffer = new StringBuilder();
		if (object instanceof Calendar) {
			final Calendar calendar = (Calendar) object;
			final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			final String formattedDate = dateFormat.format(calendar.getTime());
			buffer.append("\"");
			if (formattedDate.endsWith("00:00:00")) {
				buffer.append(formattedDate.substring(0, 10));
			} else {
				buffer.append(formattedDate);
			}
			buffer.append("\"");
		} else {
			throw new UnsupportedOperationException("CalendarToString cannot convert " + object);
		}
		return buffer.toString();
	}

	public boolean supports(Object object) {
		return object instanceof Calendar;
	}

}
